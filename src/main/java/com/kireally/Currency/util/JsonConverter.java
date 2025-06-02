package com.kireally.Currency.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Json deserializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object obj){
        try{
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Json serializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
