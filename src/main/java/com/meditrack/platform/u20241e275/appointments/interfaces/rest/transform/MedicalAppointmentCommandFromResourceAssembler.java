package com.meditrack.platform.u20241e275.appointments.interfaces.rest.transform;

import com.meditrack.platform.u20241e275.appointments.domain.model.commands.CreateMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.UpdateMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentSchedule;
import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentStatus;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.CreateMedicalAppointmentResource;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.UpdateMedicalAppointmentResource;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.DoctorId;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.Money;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.PatientId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public final class MedicalAppointmentCommandFromResourceAssembler {

    private MedicalAppointmentCommandFromResourceAssembler() {}

    public static CreateMedicalAppointmentCommand toCommandFromResource(CreateMedicalAppointmentResource resource) {
        return new CreateMedicalAppointmentCommand(
                new AppointmentCode(UUID.fromString(resource.appointmentCode())),
                new PatientId(UUID.fromString(resource.patientId())),
                new DoctorId(UUID.fromString(resource.doctorId())),
                new AppointmentSchedule(
                        LocalDate.parse(resource.appointmentDate()),
                        LocalTime.parse(resource.appointmentTime())
                ),
                new Money(resource.consultationFeeValue(), resource.consultationFeeCurrency()),
                AppointmentStatus.valueOf(resource.status()),
                resource.reason()
        );
    }

    public static UpdateMedicalAppointmentCommand toCommandFromResource(Long id, UpdateMedicalAppointmentResource resource) {
        return new UpdateMedicalAppointmentCommand(
                id,
                new AppointmentCode(UUID.fromString(resource.appointmentCode())),
                new PatientId(UUID.fromString(resource.patientId())),
                new DoctorId(UUID.fromString(resource.doctorId())),
                new AppointmentSchedule(
                        LocalDate.parse(resource.appointmentDate()),
                        LocalTime.parse(resource.appointmentTime())
                ),
                new Money(resource.consultationFeeValue(), resource.consultationFeeCurrency()),
                AppointmentStatus.valueOf(resource.status()),
                resource.reason()
        );
    }
}
