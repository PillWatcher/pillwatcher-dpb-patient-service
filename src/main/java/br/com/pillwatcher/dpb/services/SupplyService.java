package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Supply;
import io.swagger.model.SupplyForCreateDTO;

public interface SupplyService {

    Supply createSupply(SupplyForCreateDTO body);
}
