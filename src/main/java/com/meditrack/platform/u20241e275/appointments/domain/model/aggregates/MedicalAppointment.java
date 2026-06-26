package com.meditrack.platform.u20241e275.appointments.domain.model.aggregates;

import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentSchedule;
import com.meditrack.platform.u20241e275.appointments.domain.model.valueobjects.AppointmentStatus;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.DoctorId;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.Money;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.PatientId;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class MedicalAppointment extends AbstractAggregateRoot<MedicalAppointment> {

    private Long id;
    private AppointmentCode appointmentCode;
    private PatientId patientId;
    private DoctorId doctorId;
    private AppointmentSchedule schedule;
    private Money consultationFee;
    private AppointmentStatus status;
    private String reason;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public MedicalAppointment() {}

    public MedicalAppointment(Long id, AppointmentCode appointmentCode, PatientId patientId, DoctorId doctorId,
                              AppointmentSchedule schedule, Money consultationFee, AppointmentStatus status,
                              String reason, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.appointmentCode = appointmentCode;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.schedule = schedule;
        this.consultationFee = consultationFee;
        this.status = status;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public MedicalAppointment(AppointmentCode appointmentCode, PatientId patientId, DoctorId doctorId,
                              AppointmentSchedule schedule, Money consultationFee, String reason) {
        this.appointmentCode = appointmentCode;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.schedule = schedule;
        this.consultationFee = consultationFee;
        this.status = AppointmentStatus.SCHEDULED;
        this.reason = reason;
    }

    public void update(AppointmentCode appointmentCode, PatientId patientId, DoctorId doctorId,
                       AppointmentSchedule schedule, Money consultationFee, AppointmentStatus status, String reason) {
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("appointments.error.medicalAppointment.cannotDeleteCompletedMedicalAppointment");
        }
        this.appointmentCode = appointmentCode;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.schedule = schedule;
        this.consultationFee = consultationFee;
        this.status = status;
        this.reason = reason;
    }

    public void delete() {
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("appointments.error.medicalAppointment.cannotDeleteCompletedMedicalAppointment");
        }
        this.deletedAt = Instant.now();
    }
}
