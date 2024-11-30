package uz.pdp.food_recipe_app.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ErrorResponse;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper jacksonObjectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        log.error("Responding with 401 unauthorized error. URL -  {}, Message - {}", request.getRequestURI(), ex.getMessage());

        ApiResult<ErrorResponse> errorDataApiResult = ApiResult.errorResponse("Unauthorized", 401);
        response.getWriter().write(jacksonObjectMapper.writeValueAsString(errorDataApiResult));

        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
