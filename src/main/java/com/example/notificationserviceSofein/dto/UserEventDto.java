package com.example.notificationserviceSofein.dto;

import lombok.Value;

@Value
public class UserEventDto {
    String email;
    String operation; // "CREATED" или "DELETED"
}
