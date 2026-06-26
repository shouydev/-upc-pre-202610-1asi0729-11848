package com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.adapters;

import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.domain.repositories.MedicalAppointmentRepository;
import com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.assemblers.MedicalAppointmentPersistenceAssembler;
import com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.entities.MedicalAppointmentPersistenceEntity;
import com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.repositories.MedicalAppointmentPersistenceRepository;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MedicalAppointmentRepositoryImpl implements MedicalAppointmentRepository {

    private final MedicalAppointmentPersistenceRepository medicalAppointmentPersistenceRepository;

    public MedicalAppointmentRepositoryImpl(MedicalAppointmentPersistenceRepository medicalAppointmentPersistenceRepository) {
        this.medicalAppointmentPersistenceRepository = medicalAppointmentPersistenceRepository;
    }

    @Override
    public MedicalAppointment save(MedicalAppointment medicalAppointment) {
        MedicalAppointmentPersistenceEntity entity = MedicalAppointmentPersistenceAssembler.toPersistenceEntity(medicalAppointment);
        MedicalAppointmentPersistenceEntity savedEntity = medicalAppointmentPersistenceRepository.save(entity);

        return MedicalAppointmentPersistenceAssembler.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<MedicalAppointment> findById(Long id) {
        return medicalAppointmentPersistenceRepository.findById(id)
                .map(MedicalAppointmentPersistenceAssembler::toDomainEntity);
    }

    @Override
    public boolean existsByAppointmentCode(AppointmentCode appointmentCode) {
        return medicalAppointmentPersistenceRepository.existsByAppointmentCode(appointmentCode);
    }
}
