package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepository extends JpaRepository<Nurse, Long> {
}
