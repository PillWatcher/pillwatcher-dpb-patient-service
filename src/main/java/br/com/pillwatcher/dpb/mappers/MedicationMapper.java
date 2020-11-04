package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Medication;
import io.swagger.model.PrescriptionMedicationDTOForCreate;
import io.swagger.model.PrescriptionMedicationDTOForResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

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
}
