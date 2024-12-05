package org.example.amanzatboxservice.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.amanzatboxservice.anotation.RoleCheck;
import org.example.amanzatboxservice.dto.KafkaMessage;
import org.example.amanzatboxservice.dto.TokenValidateRequest;
import org.example.amanzatboxservice.dto.TokenValidateResponse;
import org.example.amanzatboxservice.kafka.KafkaRequestReply;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class JwtValidationAspect {

    private final KafkaRequestReply kafkaRequestReply;
    private final ObjectMapper objectMapper;

    @Before("@annotation(roleCheck)")
    public void validateUserRole(JoinPoint joinPoint, RoleCheck roleCheck) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = extractSessionIdFromCookies(request);

        String requiredRole = roleCheck.role();

        if (sessionId == null) {
            throw new RuntimeException("Unauthorized: No session ID.");
        }

        TokenValidateRequest tokenValidateRequest = new TokenValidateRequest();
        tokenValidateRequest.setResponseTopic("amanzat.box.response");
        tokenValidateRequest.setToken(sessionId);

        String tokenValidateRequestJson = objectMapper.writeValueAsString(tokenValidateRequest);
        KafkaMessage kafkaMessage = kafkaRequestReply.sendRequest(tokenValidateRequestJson, "amanzat.user-api.validateToken").get();
        TokenValidateResponse tokenValidateResponse = objectMapper.readValue(kafkaMessage.getData(), TokenValidateResponse.class);

        if (!tokenValidateResponse.isStatus()) {
            throw new RuntimeException("Access Denied: Invalid token.");
        }

        String userRole = tokenValidateResponse.getRole();

        if (!userRole.equals(requiredRole)) {
            throw new RuntimeException("Access Denied: Role is invalid.");
        }
    }

    private String extractSessionIdFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("session_id".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}