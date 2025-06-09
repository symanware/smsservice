package com.example.sms.controller;

import com.example.sms.exception.ResourceNotCreatedException;
import com.example.sms.model.OptOut;
import com.example.sms.service.OptOutService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptOutController {

    @Autowired
    private OptOutService optOutService;

    @PostMapping("/optout/{phoneNumber}")
    public ResponseEntity<String> optOut(@PathVariable("phoneNumber") @NotNull @NotBlank @Pattern(regexp = "^\\+\\d{10,15}$") String phoneNumber) {
        OptOut optOut = optOutService.optOut(phoneNumber);
        if(optOut ==  null)
            throw new ResourceNotCreatedException("Resource not created");
        return new ResponseEntity<>("Phone number "+optOut.getPhoneNumber() +" opted out successfully.", HttpStatus.CREATED);

    }

}
