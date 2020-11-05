package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Prescription;
import io.swagger.model.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PrescriptionMapper {

    @Mappings({
            @Mapping(target = "patient.id", source = "patientId")
    })
    Prescription dtoToEntity(final PatientPrescriptionDTOForCreate prescriptionMedicationDTOForCreate,
                             final Long patientId);

    @Mappings({
            @Mapping(target = "patientCpf", source = "prescription.patient.user.document")
    })
    PatientPrescriptionDTOForResponse entityToDto(final Prescription prescription);

    @IterableMapping(qualifiedByName = "entityToDto")
    PatientPrescriptionDTOForGetAll entityToDtos(final List<Prescription> prescription);


    @Mappings({
            @Mapping(target = "prescriptionId", source = "prescription.id"),
            @Mapping(target = "validityDate", source = "prescription.validityDate"),
            @Mapping(target = "medication", source = "medicationDTOS")
    })
    PrescriptionToPatientDTO entityToPrescriptionDetail(final Prescription prescription,
                                                        final List<MedicationDTO> medicationDTOS);
}
