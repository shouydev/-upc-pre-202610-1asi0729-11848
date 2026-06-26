package com.meditrack.platform.u20241e275.appointments.application.commandservices;

import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.CreateMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.DeleteMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.UpdateMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.shared.application.result.Result;

public interface MedicalAppointmentCommandService {

    Result<MedicalAppointment, MedicalAppointmentCommandFailure> handle(UpdateMedicalAppointmentCommand command);

    Result<MedicalAppointment, MedicalAppointmentCommandFailure> handle(DeleteMedicalAppointmentCommand command);

    Result<MedicalAppointment, MedicalAppointmentCommandFailure> handle(CreateMedicalAppointmentCommand command);
}
