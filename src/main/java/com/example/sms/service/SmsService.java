package com.example.sms.service;

import com.example.sms.model.MessageStatus;
import com.example.sms.model.SmsMessage;
import com.example.sms.model.SmsRequest;
import com.example.sms.repository.OptOutRepository;
import com.example.sms.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SmsService {

    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private OptOutRepository optOutRepository;

    SmsService(SmsRepository smsRepository, OptOutRepository optOutRepository) {
        this.smsRepository = smsRepository;
        this.optOutRepository = optOutRepository;
    }

    public SmsMessage sendSmsMessage(SmsRequest request) {

        SmsMessage smsMessage = new SmsMessage(null, request.getDestinationNumber(), request.getContent(), request.getFormat(), MessageStatus.PENDING);
        if (exitByPhoneNumber(request.getDestinationNumber())) {

            smsMessage.setStatus(MessageStatus.BLOCKED);
            return saveToSMSRepo(smsMessage);

        }
        smsMessage.setStatus(MessageStatus.PENDING);

        switch (request.getDestinationNumber().substring(0, 3)) {

            case "+61" -> {
                smsMessage.setStatus(MessageStatus.SENT);
                MessageStatus messageStatus = routeToTelstraOrOptusCarrier(smsMessage);
                smsMessage.setStatus(messageStatus);
            }
            case "+64" -> {
                smsMessage.setStatus(MessageStatus.SENT);
                MessageStatus messageStatus = routeSparkCarrier(smsMessage);
                smsMessage.setStatus(messageStatus);
            }
            default -> {
                smsMessage.setStatus(MessageStatus.SENT);
                MessageStatus messageStatus = routeGlobalCarrier(smsMessage);
                smsMessage.setStatus(messageStatus);
            }
        }
        return saveToSMSRepo(smsMessage);
    }

    private SmsMessage saveToSMSRepo(SmsMessage smsMessage) {
        return smsRepository.save(smsMessage);
    }

    private MessageStatus routeGlobalCarrier(SmsMessage request) {
        return MessageStatus.DELIVERED;
    }

    private MessageStatus routeSparkCarrier(SmsMessage request) {
        return MessageStatus.DELIVERED;
    }

    private MessageStatus routeToTelstraOrOptusCarrier(SmsMessage request) {
        return MessageStatus.DELIVERED;
    }

    public Optional<SmsMessage> getMessage(Long id) {
        return smsRepository.findById(id);
    }

    public boolean exitByPhoneNumber(String phoneNumber) {
        return optOutRepository.existsByPhoneNumber(phoneNumber);

    }

}
