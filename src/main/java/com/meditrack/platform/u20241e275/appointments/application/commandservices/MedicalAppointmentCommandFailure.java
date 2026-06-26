package com.meditrack.platform.u20241e275.appointments.application.commandservices;

public sealed interface MedicalAppointmentCommandFailure permits
        MedicalAppointmentCommandFailure.NotFound,
        MedicalAppointmentCommandFailure.InvalidState,
        MedicalAppointmentCommandFailure.Duplicate {
    record NotFound(String message) implements MedicalAppointmentCommandFailure {}

    record InvalidState(String message) implements MedicalAppointmentCommandFailure {}

    record Duplicate(String message) implements MedicalAppointmentCommandFailure {}
}
