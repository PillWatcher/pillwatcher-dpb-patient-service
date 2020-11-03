package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Prescription;
import io.swagger.model.PatientPrescriptionDTOForCreate;
import io.swagger.model.PatientPrescriptionDTOForGetAll;
import io.swagger.model.PatientPrescriptionDTOForResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
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

}
