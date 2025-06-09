package com.example.sms.repository;

import com.example.sms.model.OptOut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptOutRepository extends JpaRepository<OptOut, String> {
    boolean existsByPhoneNumber(String phoneNumber);
}