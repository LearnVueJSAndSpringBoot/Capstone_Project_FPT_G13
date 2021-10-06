package com.university.fpt.acf.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetAllRoleVO {
    private Long idRole;
    private String code;
    private String roleName;
    private LocalDate time;
}