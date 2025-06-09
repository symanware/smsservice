package com.example.sms.service;

import com.example.sms.model.OptOut;
import com.example.sms.repository.OptOutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OptOutServiceTest {

    @InjectMocks
    private OptOutService optOutService;

    @Mock
    private OptOutRepository optOutRepository;

    @Test
    void shouldAddNumberToOptOut() {
        OptOut optOut = new OptOut(1L,"+61234567890");

        when(optOutRepository.save(any())).thenReturn(optOut);
        OptOut response  = optOutService.optOut("+61234567890");

        assertEquals(1, response.getId());
        assertEquals("+61234567890", response.getPhoneNumber());
    }

}
