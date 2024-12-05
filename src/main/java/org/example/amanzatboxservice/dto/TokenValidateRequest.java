package org.example.amanzatboxservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidateRequest {
    private String responseTopic;
    private String token;
}
