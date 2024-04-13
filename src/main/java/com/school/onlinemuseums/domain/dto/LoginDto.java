package com.school.onlinemuseums.domain.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginDto implements Serializable {
    private String studentUsername;
    private Long studentId;
    private String studentPassword;
}
