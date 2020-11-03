package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
