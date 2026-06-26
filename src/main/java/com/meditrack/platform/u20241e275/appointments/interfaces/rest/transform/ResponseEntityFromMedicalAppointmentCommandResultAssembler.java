package com.meditrack.platform.u20241e275.appointments.interfaces.rest.transform;

import com.meditrack.platform.u20241e275.appointments.application.commandservices.MedicalAppointmentCommandFailure;
import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.MedicalAppointmentResource;
import com.meditrack.platform.u20241e275.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public final class ResponseEntityFromMedicalAppointmentCommandResultAssembler {

    private ResponseEntityFromMedicalAppointmentCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<MedicalAppointment, MedicalAppointmentCommandFailure> result,
            MessageSource messageSource) {
        return toResponseEntityFromResult(result, messageSource, HttpStatus.OK);
    }

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<MedicalAppointment, MedicalAppointmentCommandFailure> result,
            MessageSource messageSource,
            HttpStatus successStatus) {
        return result.fold(
                success -> new ResponseEntity<>(
                        MedicalAppointmentResourceFromAggregateAssembler.toResourceFromAggregate(success),
                        successStatus
                ),
                failure -> {
                    HttpStatus status = statusFromFailure(failure);
                    String localizedMessage = messageFromFailure(failure, messageSource);
                    return ResponseEntity.status(status).body(
                            ProblemDetail.forStatusAndDetail(status, localizedMessage)
                    );
                }
        );
    }

    private static HttpStatus statusFromFailure(MedicalAppointmentCommandFailure failure) {
        return switch (failure) {
            case MedicalAppointmentCommandFailure.Duplicate(String _) -> HttpStatus.CONFLICT;
            case MedicalAppointmentCommandFailure.NotFound(String _) -> HttpStatus.NOT_FOUND;
            case MedicalAppointmentCommandFailure.InvalidState(String _) -> HttpStatus.BAD_REQUEST;
        };
    }

    private static String messageFromFailure(MedicalAppointmentCommandFailure failure, MessageSource messageSource) {
        String messageKey = switch (failure) {
            case MedicalAppointmentCommandFailure.Duplicate(String message) -> message;
            case MedicalAppointmentCommandFailure.NotFound(String message) -> message;
            case MedicalAppointmentCommandFailure.InvalidState(String message) -> message;
        };
        try {
            return messageSource.getMessage(messageKey, null, Locale.getDefault());
        } catch (Exception e) {
            return messageKey;
        }
    }
}
