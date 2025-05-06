package com.application.SpringBoot.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class userResponse {
    private int id;
    private String username;
    private String message;
}