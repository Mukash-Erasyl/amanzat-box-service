package org.example.amanzatboxservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidateResponse {
    private String role;
    private long expiredAt;
    private int statusCode;
    private boolean status;
}
