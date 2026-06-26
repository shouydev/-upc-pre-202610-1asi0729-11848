package com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources;

import java.math.BigDecimal;

public record MedicalAppointmentResource(
        Long id,
        String appointmentCode,
        String patientId,
        String doctorId,
        String appointmentDate,
        String appointmentTime,
        BigDecimal consultationFeeValue,
        String consultationFeeCurrency,
        String status,
        String reason
) {
}
