package org.sopt.practice.common.dto;

public record SuccessStatusResponse(
        int stauts,
        String message
) {

    public static SuccessStatusResponse of(SuccessMessage successMessage) {
        return new SuccessStatusResponse(successMessage.getStatus(), successMessage.getMessage());
    }
}
