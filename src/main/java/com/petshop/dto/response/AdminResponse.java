package com.petshop.dto.response;

import com.petshop.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {

    private Long id;
    private String username;
    private String email;
    private Admin.Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
