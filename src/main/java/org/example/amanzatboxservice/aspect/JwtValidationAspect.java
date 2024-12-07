package org.example.amanzatboxservice.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.amanzatboxservice.anotation.RoleCheck;
import org.example.amanzatboxservice.dto.TokenValidateResponse;
import org.example.amanzatboxservice.kafka.KafkaConsumer;
import org.example.amanzatboxservice.kafka.KafkaRequestReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class JwtValidationAspect {
    private static final Logger log = LoggerFactory.getLogger(JwtValidationAspect.class);

    private final KafkaRequestReply kafkaRequestReply;
    private final ObjectMapper objectMapper;

    @Before("@annotation(roleCheck)")
    public void validateUserRole(RoleCheck roleCheck) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String sessionId = extractSessionIdFromCookies(request);

        String requiredRole = roleCheck.role();

        if (sessionId == null) {
            throw new RuntimeException("Unauthorized: No session ID.");
        }

        String tokenValidateRequestJson = objectMapper.writeValueAsString(sessionId);
        var kafkaMessage = kafkaRequestReply.sendRequest(tokenValidateRequestJson, "amanzat.user-api.validateToken").get();

        TokenValidateResponse tokenValidateResponse;
        tokenValidateResponse = objectMapper.convertValue(kafkaMessage.getData(), TokenValidateResponse.class);
        log.info("TokenValidateResponse kafkaMessage: {}", kafkaMessage);

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