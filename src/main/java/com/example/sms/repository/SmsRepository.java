package com.example.sms.repository;

import com.example.sms.model.SmsMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<SmsMessage, Long> {

}
