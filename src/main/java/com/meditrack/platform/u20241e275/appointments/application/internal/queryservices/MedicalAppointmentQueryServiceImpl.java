package com.meditrack.platform.u20241e275.appointments.application.internal.queryservices;

import com.meditrack.platform.u20241e275.appointments.application.queryservices.MedicalAppointmentQueryService;
import com.meditrack.platform.u20241e275.appointments.domain.model.aggregates.MedicalAppointment;
import com.meditrack.platform.u20241e275.appointments.domain.model.queries.GetMedicalAppointmentByIdQuery;
import com.meditrack.platform.u20241e275.appointments.domain.repositories.MedicalAppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MedicalAppointmentQueryServiceImpl implements MedicalAppointmentQueryService {

    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentQueryServiceImpl(MedicalAppointmentRepository medicalAppointmentRepository) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Optional<MedicalAppointment> handle(GetMedicalAppointmentByIdQuery query) {
        if (query.id() == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        return medicalAppointmentRepository.findById(query.id());
    }
}
