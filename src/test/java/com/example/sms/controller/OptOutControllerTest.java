package com.example.sms.controller;

import com.example.sms.model.OptOut;
import com.example.sms.service.OptOutService;
import com.example.sms.service.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptOutController.class)
public class OptOutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OptOutService optOutService;

    @Test
    public void optoutPhoneNumberTest() throws Exception {

        when(optOutService.optOut(any(String.class))).thenReturn(new OptOut(1L, "+65234567890"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/optout/{phoneNumber}", "+65234567890")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}
