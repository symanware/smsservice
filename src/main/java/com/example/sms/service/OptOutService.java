package com.example.sms.service;

import com.example.sms.model.OptOut;
import com.example.sms.repository.OptOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptOutService {

    @Autowired
    private OptOutRepository optOutRepository;

    public OptOut optOut(String destinationNumber) {
        OptOut optOut = new OptOut(null, destinationNumber);
        return optOutRepository.save(optOut);
    }

}
