package com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentSchedule(LocalDate appointmentDate, LocalTime appointmentTime) {
    private static final String APPOINTMENT_DATE_ERROR = "appointments.error.appointmentDate.invalid";

    public AppointmentSchedule {
        if (appointmentDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(APPOINTMENT_DATE_ERROR);
        }
    }

    public boolean isToday(LocalDate checkDate) {
        return this.appointmentDate.equals(checkDate);
    }
}
