package uz.pdp.food_recipe_app.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.food_recipe_app.config.UserPrincipal;
import uz.pdp.food_recipe_app.util.BaseConstants;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        GlobalVar.clearContext();
        GlobalVar.initStartTime();

        String X_REQUEST_ID = request.getHeader("X-Request-ID");
        GlobalVar.setRequestId(Optional.ofNullable(X_REQUEST_ID).orElse(UUID.randomUUID().toString()));

        try {
            String authorization = request.getHeader(BaseConstants.AUTHENTICATION_HEADER);

            if (authorization != null) {
                UserPrincipal userPrincipal = getUserFromBearerToken(authorization);
                if (userPrincipal != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    GlobalVar.setUserPrincipal(userPrincipal);
                    GlobalVar.setUser(userPrincipal.user());
                    GlobalVar.setUserUuid(userPrincipal.getId());
                }
            }
        } catch (Exception e) {
            log.error("SET_USER_PRINCIPAL_IF_ALL_OK_METHOD_ERROR", e);
        }

        filterChain.doFilter(request, response);
    }

    public UserPrincipal getUserFromBearerToken(String token) {

        token = token.trim();
        if (token.startsWith(BaseConstants.BEARER_TOKEN)) {
            token = token.substring(BaseConstants.BEARER_TOKEN.length()).trim();

            String userEmail = jwtTokenProvider.extractUsername(token);

            if (userEmail != null) {
                return (UserPrincipal) userDetailsService.loadUserByUsername(userEmail);
            }
        }
        return null;
    }

}
