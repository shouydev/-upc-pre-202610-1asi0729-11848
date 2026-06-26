package com.meditrack.platform.u20241e275.appointments.domain.repositories;

import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;

import java.util.Optional;

public interface MedicalAppointmentRepository {
    MedicalAppointment save(MedicalAppointment medicalAppointment);
    Optional<MedicalAppointment> findById(Long id);
    boolean existsByAppointmentCode(AppointmentCode appointmentCode);
}
