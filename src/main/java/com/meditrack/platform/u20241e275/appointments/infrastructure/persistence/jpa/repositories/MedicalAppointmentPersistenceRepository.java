package com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.repositories;

import com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.entities.MedicalAppointmentPersistenceEntity;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalAppointmentPersistenceRepository extends JpaRepository<MedicalAppointmentPersistenceEntity, Long> {
    boolean existsByAppointmentCode(AppointmentCode appointmentCode);
}
