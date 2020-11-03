package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Patient;
import io.swagger.model.PatientDTOForCreate;
import io.swagger.model.PatientDTOForGet;

import io.swagger.model.PatientDTOForResponse;
import io.swagger.model.PatientDTOForUpdate;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PatientMapper {

    @Mappings({
            @Mapping(source = "name", target = "user.name"),
            @Mapping(source = "document", target = "user.document"),
            @Mapping(source = "imageUrl", target = "user.imageUrl")
    })
    Patient toPatientForCreateEntity(PatientDTOForCreate patientDTOForCreate);

    PatientDTOForCreate toPatientDtoForCreate(Patient patient);

    @Mappings({
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.imageUrl", target = "imageUrl")
    })
    PatientDTOForUpdate toPatientForUpdate(Patient patient);

    @Mappings({
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.document", target = "document"),
            @Mapping(source = "user.imageUrl", target = "imageUrl")
    })
    PatientDTOForResponse toPatientForResponse(Patient patient);

    @IterableMapping(qualifiedByName = "toPatientForResponse")
    List<PatientDTOForResponse> toPatientForResponse(List<Patient> patient);

    PatientDTOForGet toPatientDtoForGet(List<PatientDTOForResponse> patients);


}
