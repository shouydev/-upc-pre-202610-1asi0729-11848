package com.meditrack.platform.u20241e275.appointments.interfaces.rest.transform;

import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.MedicalAppointmentResource;

public final class MedicalAppointmentResourceFromAggregateAssembler {

    private MedicalAppointmentResourceFromAggregateAssembler() {}

    public static MedicalAppointmentResource toResourceFromAggregate(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResource(
                medicalAppointment.getId(),
                medicalAppointment.getAppointmentCode().value().toString(),
                medicalAppointment.getPatientId().value().toString(),
                medicalAppointment.getDoctorId().value().toString(),
                medicalAppointment.getSchedule().appointmentDate().toString(),
                medicalAppointment.getSchedule().appointmentTime().toString(),
                medicalAppointment.getConsultationFee().value(),
                medicalAppointment.getConsultationFee().currency(),
                medicalAppointment.getStatus().toString(),
                medicalAppointment.getReason()
        );
    }
}
