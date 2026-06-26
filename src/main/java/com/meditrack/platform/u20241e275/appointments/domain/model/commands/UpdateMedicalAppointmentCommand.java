package com.meditrack.platform.u20241e275.appointments.domain.model.commands;

import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentSchedule;
import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentStatus;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.DoctorId;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.Money;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.PatientId;

public record UpdateMedicalAppointmentCommand(
        Long id,
        AppointmentCode appointmentCode,
        PatientId patientId,
        DoctorId doctorId,
        AppointmentSchedule schedule,
        Money consultationFee,
        AppointmentStatus appointmentStatus,
        String reason
) {
}
