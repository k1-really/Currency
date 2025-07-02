package com.kireally.Currency.model.dto.rate;

import lombok.Data;

@Data
public class ConvertResponse {
    String from;
    String to;
    double amount;
    double converted_amount;
}

