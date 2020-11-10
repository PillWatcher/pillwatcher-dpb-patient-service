package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.AppliedMedication;
import br.com.pillwatcher.dpb.entities.Medication;
import br.com.pillwatcher.dpb.entities.MqttMedication;
import io.swagger.model.MedicationDTO;
import io.swagger.model.PrescriptionMedicationDTOForAll;
import io.swagger.model.PrescriptionMedicationDTOForCreate;
import io.swagger.model.PrescriptionMedicationDTOForResponse;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MedicationMapper {

    @Mappings({
            @Mapping(target = "medicine.id", source = "medicineId"),
            @Mapping(target = "prescription.id", source = "prescriptionId"),
            @Mapping(target = "intervalTime", source = "prescriptionMedicationDTOForCreate.interval"),
            @Mapping(target = "cup.id", source = "cupId"),
            @Mapping(target = "quantity", source = "prescriptionMedicationDTOForCreate.quantity"),
            @Mapping(target = "location", source = "prescriptionMedicationDTOForCreate.location")
    })
    Medication dtoToEntity(final PrescriptionMedicationDTOForCreate prescriptionMedicationDTOForCreate,
                           final Long prescriptionId, final Long cupId, final Long medicineId);

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

    @Mappings({
            @Mapping(target = "nurse.id", source = "mqttMedication.nurseId"),
            @Mapping(target = "medication.id", source = "mqttMedication.medicationId"),
            @Mapping(target = "nextMedicationDate", source = "nextMedicationDate"),
            @Mapping(target = "medicationDate", source = "medicationDate")
    })
    AppliedMedication mqttToMedication(final MqttMedication mqttMedication,
                                       final LocalDateTime medicationDate,
                                       final LocalDateTime nextMedicationDate);
}
