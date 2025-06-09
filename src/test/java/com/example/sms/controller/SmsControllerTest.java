package com.example.sms.controller;


import com.example.sms.exception.ResourceNotFoundException;
import com.example.sms.model.MessageStatus;
import com.example.sms.model.SmsMessage;
import com.example.sms.model.SmsRequest;
import com.example.sms.service.SmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SmsController.class)
public class SmsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SmsService smsService;

    @Test
    public void sendMesssageTest() throws Exception{

        SmsMessage message = new SmsMessage(1L, "+61234567890", "Sending message to AU phone number", "SMS", MessageStatus.DELIVERED);
        SmsRequest smsRequest = new SmsRequest("+64123456789","This is test message","SMS");
        when(smsService.sendSmsMessage(any(SmsRequest.class))).thenReturn(message);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(smsRequest)));

        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(message.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageId", Matchers.is(message.getId().intValue())));
    }


    @Test
    public void getMessageStatusTest() throws Exception{

        SmsMessage message = new SmsMessage(1L, "+61234567890", "Sending message to AU phone number", "SMS", MessageStatus.DELIVERED);

        when(smsService.getMessage(any(Long.class))).thenReturn(Optional.of(message));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(message.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(message.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destinationNumber", Matchers.is(message.getDestinationNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(message.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.format", Matchers.is(message.getFormat())));
    }


    @Test
    public void throwResourceNotFoundException_whenNoMessageFound() throws Exception {

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> {
                    throw new ResourceNotFoundException("Message not found for given Id");
                });
        when(smsService.getMessage(any(Long.class))).thenThrow(exception);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(status().isNotFound())
                .andDo(print());

        assertEquals("Message not found for given Id", exception.getMessage());

    }

}
