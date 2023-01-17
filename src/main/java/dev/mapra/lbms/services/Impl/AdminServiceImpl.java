package dev.mapra.lbms.services.Impl;

import dev.mapra.lbms.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {
    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}
