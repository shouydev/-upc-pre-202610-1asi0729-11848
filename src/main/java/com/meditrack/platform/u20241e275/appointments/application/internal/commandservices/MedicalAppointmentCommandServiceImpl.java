package com.meditrack.platform.u20241e275.appointments.application.internal.commandservices;

import com.meditrack.platform.u20241e275.appointments.application.commandservices.MedicalAppointmentCommandFailure;
import com.meditrack.platform.u20241e275.appointments.application.commandservices.MedicalAppointmentCommandService;
import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.CreateMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.DeleteMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.UpdateMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.repositories.MedicalAppointmentRepository;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.CreateMedicalAppointmentResource;
import com.meditrack.platform.u20241e275.shared.application.result.Result;
import com.meditrack.platform.u20241e275.shared.domain.model.valueobjects.AppointmentCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MedicalAppointmentCommandServiceImpl implements MedicalAppointmentCommandService {

    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentCommandServiceImpl(MedicalAppointmentRepository medicalAppointmentRepository) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    @Transactional
    public Result<MedicalAppointment, MedicalAppointmentCommandFailure> handle(CreateMedicalAppointmentCommand command) {
        try {
            if (medicalAppointmentRepository.existsByAppointmentCode(command.appointmentCode())) {
                throw new IllegalStateException("appointments.error.medicalAppointment.alreadyExists");
            }

            MedicalAppointment medicalAppointment = new MedicalAppointment(
                    command.appointmentCode(),
                    command.patientId(),
                    command.doctorId(),
                    command.schedule(),
                    command.consultationFee(),
                    command.reason()
            );

            MedicalAppointment savedMedicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
            return Result.success(savedMedicalAppointment);

        } catch (IllegalArgumentException e) {
            return Result.failure(new MedicalAppointmentCommandFailure.NotFound(e.getMessage()));
        } catch (IllegalStateException e) {
            return Result.failure(new MedicalAppointmentCommandFailure.InvalidState(e.getMessage()));
        }
    }

    @Override
    public Result<MedicalAppointment, MedicalAppointmentCommandFailure> handle(UpdateMedicalAppointmentCommand command) {
        try {
            MedicalAppointment medicalAppointment = medicalAppointmentRepository.findById(command.id())
                    .orElseThrow(() -> new IllegalArgumentException("appointments.error.medicalAppointment.notFound"));

            medicalAppointment.update(command.appointmentCode(), command.patientId(), command.doctorId(), command.schedule(), command.consultationFee(), command.appointmentStatus(), command.reason());

            MedicalAppointment savedMedicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
            return Result.success(savedMedicalAppointment);
        } catch (IllegalArgumentException e) {
            return Result.failure(new MedicalAppointmentCommandFailure.NotFound(e.getMessage()));
        } catch (IllegalStateException e) {
            return Result.failure(new MedicalAppointmentCommandFailure.InvalidState(e.getMessage()));
        }
    }

    @Override
    public Result<MedicalAppointment, MedicalAppointmentCommandFailure> handle(DeleteMedicalAppointmentCommand command) {
        try {
            MedicalAppointment medicalAppointment = medicalAppointmentRepository.findById(command.id())
                    .orElseThrow(() -> new IllegalArgumentException("appointments.error.medicalAppointment.notFound"));

            medicalAppointment.delete();
            MedicalAppointment savedmedicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
            return Result.success(savedmedicalAppointment);
        } catch (IllegalArgumentException e) {
            return Result.failure(new MedicalAppointmentCommandFailure.NotFound(e.getMessage()));
        } catch (IllegalStateException e) {
            return Result.failure(new MedicalAppointmentCommandFailure.InvalidState(e.getMessage()));
        }
    }
}
