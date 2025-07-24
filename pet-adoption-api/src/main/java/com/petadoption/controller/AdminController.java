package com.petadoption.controller;

import com.petadoption.config.UserAuthenticationProvider;
import com.petadoption.dto.AdminDto;
import com.petadoption.exception.AdminNotFoundException;
import com.petadoption.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAdmins() {
        List<AdminDto> admins = adminService.getAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable String id) throws AdminNotFoundException {
        Optional<AdminDto> admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin.orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable String id, @RequestBody AdminDto updatedAdminDto) throws AdminNotFoundException {
        Optional<AdminDto> updatedAdmin = adminService.updateAdmin(id, updatedAdminDto);
        return ResponseEntity.ok(updatedAdmin.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) throws AdminNotFoundException {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AdminDto> addAdmin(@RequestBody AdminDto adminDto) {
        AdminDto newAdmin = adminService.addAdmin(adminDto);
        return ResponseEntity.ok(newAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<AdminDto> login(@RequestBody AdminDto adminDto) {
        AdminDto admin = adminService.login(adminDto);
        admin.setToken(userAuthenticationProvider.createToken(adminDto.getEmail(), admin.getAdminId(), "ROLE_ADMIN"));
        return ResponseEntity.ok(admin);
    }
}
