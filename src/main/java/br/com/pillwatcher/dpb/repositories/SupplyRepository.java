package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
