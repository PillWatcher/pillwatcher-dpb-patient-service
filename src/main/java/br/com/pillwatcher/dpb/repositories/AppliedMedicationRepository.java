package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.AppliedMedication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppliedMedicationRepository extends JpaRepository<AppliedMedication, Long> {
}
