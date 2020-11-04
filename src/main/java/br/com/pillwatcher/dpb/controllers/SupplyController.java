package br.com.pillwatcher.dpb.controllers;

import br.com.pillwatcher.dpb.entities.Supply;
import br.com.pillwatcher.dpb.mappers.SupplyMapper;
import br.com.pillwatcher.dpb.services.SupplyService;
import io.swagger.annotations.Api;
import io.swagger.api.SuppliesApi;
import io.swagger.model.SupplyForCreateDTO;
import io.swagger.model.SupplyForResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static br.com.pillwatcher.dpb.constants.UrlConstants.BASE_URI;


@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"Supply"})
@RequestMapping(BASE_URI)
public class SupplyController implements SuppliesApi {

    private final SupplyService supplyService;

    private final SupplyMapper supplyMapper;

    @Override
    public ResponseEntity<SupplyForResponseDTO> createSupply(@Valid final SupplyForCreateDTO body) {
        log.info("SupplyController.createSupply - Start - Input - {}", body);

        Supply supply = supplyService.createSupply(body);

        ResponseEntity<SupplyForResponseDTO> response = ResponseEntity.ok(supplyMapper.entityToDto(supply));

        log.debug("SupplyController.createSupply - End - Input: {} - Output: {}", body, response);

        return response;
    }

}
