package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Medication;
import io.swagger.model.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MedicationMapper {

    @Mappings({
            @Mapping(target = "medicine.id", source = "prescriptionMedicationDTOForCreate.medicineId"),
            @Mapping(target = "prescription.id", source = "prescriptionId"),
            @Mapping(target = "intervalTime", source = "prescriptionMedicationDTOForCreate.interval"),
            @Mapping(target = "cup.id", source = "cupId"),
            @Mapping(target = "quantity", source = "prescriptionMedicationDTOForCreate.quantity"),
            @Mapping(target = "location", source = "prescriptionMedicationDTOForCreate.location")
    })
    Medication dtoToEntity(final PrescriptionMedicationDTOForCreate prescriptionMedicationDTOForCreate,
                           final Long prescriptionId, final Long cupId);

    @Mappings({
            @Mapping(target = "medicine.id", source = "medicine.id"),
            @Mapping(target = "medicine.name", source = "medicine.name"),
            @Mapping(target = "medicine.dosage", source = "medicine.dosage"),
            @Mapping(target = "medicine.inclusionDate", source = "medicine.inclusionDate"),
            @Mapping(target = "prescriptionId", source = "prescription.id"),
            @Mapping(target = "quantity", source = "quantity"),
            @Mapping(target = "interval", source = "intervalTime"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "cupTag", source = "cup.tag"),
            @Mapping(target = "availableQuantity", source = "availableQuantity")
    })
    PrescriptionMedicationDTOForResponse entityToDto(final Medication medication);

    @IterableMapping(qualifiedByName = "entityToDto")
    PrescriptionMedicationDTOForAll entitiesToDtos(final List<Medication> medication);

    @Mappings({
            @Mapping(target = "medicationId", source = "id"),
            @Mapping(target = "name", source = "medicine.name"),
            @Mapping(target = "dosage", source = "medicine.dosage"),
            @Mapping(target = "dosageType", source = "medicine.dosageType"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "intervalTime", source = "intervalTime"),
            @Mapping(target = "cupTag", source = "cup.tag"),
            @Mapping(target = "availableQuantity", source = "availableQuantity")
    })
    MedicationDTO entitiesToMedicationDetail(final Medication medication);

    @IterableMapping(qualifiedByName = "entityToMedicationDetail")
    List<MedicationDTO> entitiesToMedicationDetail(final List<Medication> medications);
}
