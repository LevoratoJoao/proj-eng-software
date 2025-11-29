package com.software.software.controller.dtos.observation;

public record RequestObservationDto(
    String message,
    String notificationType
) {
    // message: "Seu filho foi ótimo hoje"
    // notificationType: "email", "sms" ou "whatsapp"
}