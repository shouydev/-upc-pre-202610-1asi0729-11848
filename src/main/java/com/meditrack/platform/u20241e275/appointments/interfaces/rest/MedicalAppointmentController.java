package com.meditrack.platform.u20241e275.appointments.interfaces.rest;

import com.meditrack.platform.u20241e275.appointments.application.commandservices.MedicalAppointmentCommandService;
import com.meditrack.platform.u20241e275.appointments.application.queryservices.MedicalAppointmentQueryService;
import com.meditrack.platform.u20241e275.appointments.domain.model.commands.DeleteMedicalAppointmentCommand;
import com.meditrack.platform.u20241e275.appointments.domain.model.queries.GetMedicalAppointmentByIdQuery;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.CreateMedicalAppointmentResource;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.resources.UpdateMedicalAppointmentResource;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.transform.MedicalAppointmentCommandFromResourceAssembler;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.transform.MedicalAppointmentResourceFromAggregateAssembler;
import com.meditrack.platform.u20241e275.appointments.interfaces.rest.transform.ResponseEntityFromMedicalAppointmentCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/api/v1/medical-appointments", produces = "application/json")
@Tag(name = "Medical Appointments", description = "Endpoints for managing medical appointments")
public class MedicalAppointmentController {

    private final MedicalAppointmentCommandService medicalAppointmentCommandService;
    private final MedicalAppointmentQueryService medicalAppointmentQueryService;
    private final MessageSource messageSource;

    public MedicalAppointmentController(MedicalAppointmentCommandService medicalAppointmentCommandService,
                                        MedicalAppointmentQueryService medicalAppointmentQueryService,
                                        MessageSource messageSource) {
        this.medicalAppointmentCommandService = medicalAppointmentCommandService;
        this.medicalAppointmentQueryService = medicalAppointmentQueryService;
        this.messageSource = messageSource;
    }

    @PostMapping
    @Operation(summary = "Create a new medical appointment", description = "Creates a new medical appointment with the provided details.")
    public ResponseEntity<?> createMedicalAppointment(@RequestBody CreateMedicalAppointmentResource resource) {
        var command = MedicalAppointmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = medicalAppointmentCommandService.handle(command);

        return ResponseEntityFromMedicalAppointmentCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a medical appointment by ID", description = "Retrieves a medical appointment by its unique ID.")
    public ResponseEntity<?> getMedicalAppointmentById(@PathVariable Long id) {
        var query = new GetMedicalAppointmentByIdQuery(id);
        var medicalAppointment = medicalAppointmentQueryService.handle(query);

        return medicalAppointment.map(
                value -> ResponseEntity.ok(MedicalAppointmentResourceFromAggregateAssembler
                        .toResourceFromAggregate(value)))
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a medical appointment", description = "Updates an existing medical appointment.")
    public ResponseEntity<?> updateMedicalAppointment(@PathVariable Long id, @RequestBody UpdateMedicalAppointmentResource resource) {
        var command = MedicalAppointmentCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = medicalAppointmentCommandService.handle(command);

        return ResponseEntityFromMedicalAppointmentCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a medical appointment", description = "Deletes an existing medical appointment.")
    public ResponseEntity<?> deleteMedicalAppointment(@PathVariable Long id) {
        var command  = new DeleteMedicalAppointmentCommand(id);
        var result = medicalAppointmentCommandService.handle(command);

        return ResponseEntityFromMedicalAppointmentCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }
}
