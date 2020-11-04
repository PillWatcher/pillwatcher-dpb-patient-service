package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Medicine;
import io.swagger.model.MedicineDTOForAll;
import io.swagger.model.MedicineDTOForCreate;
import io.swagger.model.MedicineDTOForResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MedicineMapper {

    Medicine dtoToEntity(MedicineDTOForCreate medicineDto);

    MedicineDTOForResponse toDtoForResponse(Medicine medicine);

    @IterableMapping(qualifiedByName = "toDtoForResponse")
    MedicineDTOForAll entitiesToDtos(List<Medicine> medicine);

}
