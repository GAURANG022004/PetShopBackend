package com.petshop.config;

import com.petshop.constant.AppConstants;
import com.petshop.entity.Admin;
import com.petshop.entity.ProductCategory;
import com.petshop.repository.AdminRepository;
import com.petshop.repository.ProductCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final ProductCategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AdminRepository adminRepository, 
                          ProductCategoryRepository categoryRepository,
                          PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initializeAdmin();
        initializeCategories();
    }

    private void initializeAdmin() {
        if (!adminRepository.existsByUsername(AppConstants.ADMIN_USERNAME)) {
            Admin admin = new Admin();
            admin.setUsername(AppConstants.ADMIN_USERNAME);
            admin.setEmail(AppConstants.ADMIN_EMAIL);
            admin.setPassword(passwordEncoder.encode(AppConstants.ADMIN_PASSWORD));
            admin.setRole(Admin.Role.ADMIN);
            adminRepository.save(admin);
            System.out.println("Default admin user created successfully");
        }
    }

    private void initializeCategories() {
        List<String> categoryNames = Arrays.asList(
                "Pet Food",
                "Accessories",
                "Grooming",
                "Medicines",
                "Toys"
        );

        for (String categoryName : categoryNames) {
            if (!categoryRepository.existsByCategoryName(categoryName)) {
                ProductCategory category = new ProductCategory();
                category.setCategoryName(categoryName);
                category.setDescription("Default category for " + categoryName);
                category.setStatus(ProductCategory.Status.ACTIVE);
                categoryRepository.save(category);
            }
        }
        System.out.println("Default categories initialized successfully");
    }
}
