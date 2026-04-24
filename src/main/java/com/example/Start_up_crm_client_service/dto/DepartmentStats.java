package com.example.Start_up_crm_client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<<< HEAD:src/main/java/com/example/Start_up_crm_client_service/dto/DepartmentStats.java
public class DepartmentStats
{
    private String department;
    private Long count;
========
public class HrResponse {

    private int code;
    private boolean success;
    private String message;
    private Object data;
//    private String token;
//    private String role;
>>>>>>>> e562de879f3d9d86706bffae7a6eb4332e92acb2:src/main/java/com/example/Start_up_crm_client_service/dto/HrResponse.java
}