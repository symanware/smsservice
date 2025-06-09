package com.example.sms.controller;

import com.example.sms.exception.ResourceNotFoundException;
import com.example.sms.model.SmsMessage;
import com.example.sms.model.SmsRequest;
import com.example.sms.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/messages")
    public ResponseEntity<Map<String,Object>> sendMessage(@Valid @RequestBody SmsRequest request) {
        SmsMessage message = smsService.sendSmsMessage(request);
        if(message == null)
            ResponseEntity.badRequest().build();

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("messageId",message.getId(),"status", message.getStatus().name()));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<SmsMessage> getMessage(@PathVariable Long id) {
        Optional<SmsMessage> smsMessage = smsService.getMessage(id);
        if (smsMessage.isEmpty())
            throw new ResourceNotFoundException("Message not found for given Id");
        return new ResponseEntity<>(smsMessage.get(), HttpStatus.OK);
    }

}
