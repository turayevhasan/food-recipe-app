package uz.pdp.food_recipe_app.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.config.UserPrincipal;
import uz.pdp.food_recipe_app.config.jwt.JwtTokenProvider;
import uz.pdp.food_recipe_app.entity.Password;
import uz.pdp.food_recipe_app.entity.Role;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.enums.RoleEnum;
import uz.pdp.food_recipe_app.enums.UserStatus;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.auth.req.RefreshTokenReq;
import uz.pdp.food_recipe_app.payload.auth.req.SignInReq;
import uz.pdp.food_recipe_app.payload.auth.req.SignUpReq;
import uz.pdp.food_recipe_app.payload.auth.res.SignInRes;
import uz.pdp.food_recipe_app.payload.auth.res.TokenDto;
import uz.pdp.food_recipe_app.payload.auth.res.UserRes;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.repository.PasswordRepository;
import uz.pdp.food_recipe_app.repository.RoleRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.util.BaseConstants;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uz.pdp.food_recipe_app.enums.ErrorTypeEnum.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordService passwordService;
    private final PasswordRepository passwordRepository;
    private final AttachmentRepository attachmentRepository;

    public ResBaseMsg signUp(SignUpReq req) {
        Pattern pattern = Pattern.compile("^\\w*?[a-zA-Z]\\w+@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.[a-z]+\\z");
        Matcher matcher = pattern.matcher(req.getEmail());

        if (!matcher.matches())
            throw RestException.restThrow(EMAIL_NOT_VALID);

        if (userRepository.existsByEmail(req.getEmail()))
            throw RestException.restThrow(EMAIL_ALREADY_EXISTS);

        Role role = roleRepository.findByName(RoleEnum.USER.name())
                .orElseThrow(() -> RestException.restThrow(ROLE_NOT_FOUND));

        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        try {
            String code = passwordService.generatePassword(user);
            mailService.sendMessage(user.getEmail(), code, "Your Confirmation Code", "Complete Registration");
        } catch (MessagingException e) {
            throw RestException.restThrow(EMAIL_NOT_VALID);
        }

        return new ResBaseMsg("Success! Account confirmation code sent to your Email!");
    }


    public SignInRes signIn(SignInReq req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                ));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (!userPrincipal.user().isActive())
            throw RestException.restThrow(USER_NOT_ACTIVATED);

        return generateSignInRes(userPrincipal.user());
    }

    public TokenDto refreshToken(RefreshTokenReq req) {
        String accessToken = req.getAccessToken().trim();
        accessToken = getTokenWithOutBearer(accessToken);

        try {
            jwtTokenProvider.extractUsername(accessToken);
        } catch (ExpiredJwtException ex) {
            try {
                String refreshToken = req.getRefreshToken();
                refreshToken = getTokenWithOutBearer(refreshToken);

                String email = jwtTokenProvider.extractUsername(refreshToken);
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> RestException.restThrow(TOKEN_NOT_VALID));

                if (!user.isActive())
                    throw RestException.restThrow(USER_PERMISSION_RESTRICTION);

                return generateTokens(user);

            } catch (Exception e) {
                throw RestException.restThrow(REFRESH_TOKEN_EXPIRED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow(WRONG_ACCESS_TOKEN);
        }
        throw RestException.restThrow(ACCESS_TOKEN_NOT_EXPIRED);

    }

    private static String getTokenWithOutBearer(String token) {
        return token.startsWith(BaseConstants.BEARER_TOKEN) ?
                token.substring(token.indexOf(BaseConstants.BEARER_TOKEN) + 6).trim() :
                token.trim();
    }

    private SignInRes generateSignInRes(User user) {
        String path = null;
        if (user.getPhotoId() != null)
            path = attachmentRepository.findById(user.getPhotoId())
                    .orElseThrow(RestException.thew(FILE_NOT_FOUND))
                    .getFilePath();

        UserRes userRes = new UserRes(user, path);

        return new SignInRes(userRes, generateTokens(user));
    }

    private TokenDto generateTokens(User user) {
        String accessToken = jwtTokenProvider.generateToken(user);
        Long accessTokenExp = BaseConstants.ACCESS_TOKEN_EXPIRE;
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        Long refreshTokenExp = BaseConstants.REFRESH_TOKEN_EXPIRE;

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpire(accessTokenExp)
                .refreshTokenExpire(refreshTokenExp)
                .build();
    }

    public ResBaseMsg verifyAccount(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(RestException.thew(USER_NOT_FOUND));

        if (user.isActive())
            return new ResBaseMsg("User Already Verified!");

        Password password = passwordRepository.findByUserEmailAndDeleted(email, false)
                .orElseThrow(RestException.thew(VERIFICATION_PASSWORD_NOT_FOUND));

        if (!password.getCode().equals(code))
            throw RestException.restThrow(PASSWORD_NOT_MATCH);

        password.setDeleted(true);
        passwordRepository.save(password);

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        return new ResBaseMsg("Account verified!");
    }
}
