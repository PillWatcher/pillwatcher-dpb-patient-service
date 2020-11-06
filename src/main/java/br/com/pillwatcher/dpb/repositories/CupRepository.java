package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Cup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CupRepository extends JpaRepository<Cup, Long> {

    Optional<Cup> findByTag(final String tag);
}
