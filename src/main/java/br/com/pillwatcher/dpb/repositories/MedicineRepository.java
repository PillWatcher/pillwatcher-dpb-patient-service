package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
