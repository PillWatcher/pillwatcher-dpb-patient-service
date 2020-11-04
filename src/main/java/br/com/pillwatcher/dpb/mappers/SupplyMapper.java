package br.com.pillwatcher.dpb.mappers;

import br.com.pillwatcher.dpb.entities.Supply;
import io.swagger.model.SupplyForResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SupplyMapper {

    @Mappings({
            @Mapping(target = "nurseId", source = "nurse.id"),
            @Mapping(target = "medicationId", source = "medication.id"),
            @Mapping(target = "quantity", source = "quantity")
    })
    SupplyForResponseDTO entityToDto(final Supply supply);
}
