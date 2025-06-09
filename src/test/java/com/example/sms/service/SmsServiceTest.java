package com.example.sms.service;

import com.example.sms.model.MessageStatus;
import com.example.sms.model.SmsMessage;
import com.example.sms.model.SmsRequest;
import com.example.sms.repository.OptOutRepository;
import com.example.sms.repository.SmsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class SmsServiceTest {

    @InjectMocks
    private SmsService smsService;

    @Mock
    private SmsRepository smsRepository;

    @Mock
    private OptOutRepository optOutRepository;

    @Test
    void shouldSendAndDeliverMessageToTelstraOrOptus_WhenNotOptedOut() {
        SmsRequest request = new SmsRequest("+61234567890","Sending message to AU phone number","SMS");
        SmsMessage message = new SmsMessage(1L, "+61234567890", "Sending message to AU phone number", "SMS", MessageStatus.DELIVERED);

        Mockito.when(smsService.exitByPhoneNumber(anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(smsRepository.save(any())).thenReturn(message);

        SmsMessage response  = smsService.sendSmsMessage(request);

        assertEquals(1, response.getId());
        assertEquals(MessageStatus.DELIVERED, response.getStatus());

    }

    @Test
    void shouldSendAndDeliverMessageToSpark_WhenNotOptedOut() {
        SmsRequest request = new SmsRequest("+64234567890","Sending message to NZ phone number","SMS");
        SmsMessage message = new SmsMessage(1L, "+64234567890", "Sending message to NZ phone number", "SMS", MessageStatus.DELIVERED);

        Mockito.when(smsService.exitByPhoneNumber(anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(smsRepository.save(any())).thenReturn(message);

        SmsMessage response  = smsService.sendSmsMessage(request);

        assertEquals(1, response.getId());
        assertEquals(MessageStatus.DELIVERED, response.getStatus());

    }


    @Test
    void shouldBlockMessage_WhenOptedOut() {
        SmsRequest request = new SmsRequest("+64234567890","Sending message to NZ phone number","SMS");
        SmsMessage message = new SmsMessage(1L, "+64234567890", "Sending message to NZ phone number", "SMS", MessageStatus.BLOCKED);

        Mockito.when(smsService.exitByPhoneNumber(anyString())).thenReturn(Boolean.TRUE);
        Mockito.when(smsRepository.save(any())).thenReturn(message);

        SmsMessage response  = smsService.sendSmsMessage(request);

        assertEquals(1, response.getId());
        assertEquals(MessageStatus.BLOCKED, response.getStatus());

    }


}
