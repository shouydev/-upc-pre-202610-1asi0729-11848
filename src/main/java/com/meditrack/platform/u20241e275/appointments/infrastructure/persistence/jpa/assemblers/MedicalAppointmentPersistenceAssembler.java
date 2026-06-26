package com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.assemblers;

import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.entities.MedicalAppointmentPersistenceEntity;

public final class MedicalAppointmentPersistenceAssembler {

    public MedicalAppointmentPersistenceAssembler() {}

    public static MedicalAppointmentPersistenceEntity toPersistenceEntity(MedicalAppointment medicalAppointment) {
        if (medicalAppointment == null) return null;
        MedicalAppointmentPersistenceEntity persistenceEntity = new MedicalAppointmentPersistenceEntity();
        if (medicalAppointment.getId() != null) {
            persistenceEntity.setId(medicalAppointment.getId());
        }
        persistenceEntity.setAppointmentCode(medicalAppointment.getAppointmentCode());
        persistenceEntity.setPatientId(medicalAppointment.getPatientId());
        persistenceEntity.setDoctorId(medicalAppointment.getDoctorId());
        persistenceEntity.setSchedule(medicalAppointment.getSchedule());
        persistenceEntity.setConsultationFee(medicalAppointment.getConsultationFee());
        persistenceEntity.setStatus(medicalAppointment.getStatus());
        persistenceEntity.setReason(medicalAppointment.getReason());
        persistenceEntity.setCreatedAt(medicalAppointment.getCreatedAt());
        persistenceEntity.setUpdatedAt(medicalAppointment.getUpdatedAt());
        persistenceEntity.setDeletedAt(medicalAppointment.getDeletedAt());
        return persistenceEntity;
    }

    public static MedicalAppointment toDomainEntity(MedicalAppointmentPersistenceEntity persistenceEntity) {
        if (persistenceEntity == null) return null;
        return new MedicalAppointment(
                persistenceEntity.getId(),
                persistenceEntity.getAppointmentCode(),
                persistenceEntity.getPatientId(),
                persistenceEntity.getDoctorId(),
                persistenceEntity.getSchedule(),
                persistenceEntity.getConsultationFee(),
                persistenceEntity.getStatus(),
                persistenceEntity.getReason(),
                persistenceEntity.getCreatedAt(),
                persistenceEntity.getUpdatedAt(),
                persistenceEntity.getDeletedAt()
        );
    }
}
