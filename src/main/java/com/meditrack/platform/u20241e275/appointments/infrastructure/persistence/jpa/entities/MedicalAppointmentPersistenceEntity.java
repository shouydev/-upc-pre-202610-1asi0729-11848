package com.meditrack.platform.u20241e275.appointments.infrastructure.persistence.jpa.entities;

import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentSchedule;
import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentStatus;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.DoctorId;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.Money;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.PatientId;
import com.meditrack.platform.u20241e275.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "medical_appointments")
@SQLDelete(sql = "UPDATE medical_appointments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MedicalAppointmentPersistenceEntity extends AuditableAbstractPersistenceEntity implements Persistable<Long> {

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "appointment_code", nullable = false, unique = true, columnDefinition = "CHAR(36)"))
    private AppointmentCode appointmentCode;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "patient_id", nullable = false, columnDefinition = "CHAR(36)"))
    private PatientId patientId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "doctor_id", nullable = false, columnDefinition = "CHAR(36)"))
    private DoctorId doctorId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "appointmentDate", column = @Column(name = "appointment_date", nullable = false)),
            @AttributeOverride(name = "appointmentTime", column = @Column(name = "appointment_time", nullable = false))
    })
    private AppointmentSchedule schedule;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "consultation_fee_value", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "consultation_fee_currency", nullable = false, columnDefinition = "CHAR(3)"))
    })
    private Money consultationFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(nullable = true)
    private String reason;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
