package com.meditrack.platform.u20241e275.shared.domain.model.valueobjects;

import java.util.UUID;

public record DoctorId(UUID value) {
    private static final String NOT_NULL_UUID_REGEX = "shared.error.appointmentCode.required";

    public DoctorId {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_UUID_REGEX);
        }
    }
}
