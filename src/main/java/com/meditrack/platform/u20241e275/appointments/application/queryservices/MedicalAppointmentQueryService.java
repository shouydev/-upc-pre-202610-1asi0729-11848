package com.meditrack.platform.u20241e275.appointments.application.queryservices;

import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.domain.model.queries.GetMedicalAppointmentByIdQuery;

import java.util.Optional;

public interface MedicalAppointmentQueryService {

    Optional<MedicalAppointment> handle(GetMedicalAppointmentByIdQuery query);
}
