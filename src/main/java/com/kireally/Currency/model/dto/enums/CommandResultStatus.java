package com.kireally.Currency.model.dto.enums;

import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import lombok.Getter;

@Getter
public enum CommandResultStatus {
    SUCCESS,
    FAILED;

    public static CommandResultStatus fromString(String status){
        for(CommandResultStatus commandResultStatus : CommandResultStatus.values()){
            if(commandResultStatus.toString().equalsIgnoreCase(status)){
                return commandResultStatus;
            }
        }
        throw new IllegalArgumentException("Invalid CommandResultStatus: " + status);
    }
}
