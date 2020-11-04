package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Medicine;
import io.swagger.model.MedicineDTOForCreate;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MedicineMapper {

    Medicine dtoToEntity(MedicineDTOForCreate medicineDto);

}
