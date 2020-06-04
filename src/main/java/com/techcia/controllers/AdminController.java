package com.techcia.controllers;

import com.techcia.dtos.admin.AdminCreateDto;
import com.techcia.models.Admin;
import com.techcia.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Slf4j

@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody AdminCreateDto adminCreateDto) {
        Admin admin = adminCreateDto.convertToEntity();
        return ResponseEntity.ok(adminService.save(admin));
    }
}
