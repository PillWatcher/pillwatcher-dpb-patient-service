package br.com.pillwatcher.dpb.repositories;

import br.com.pillwatcher.dpb.entities.Medicine;
import io.swagger.model.DosageTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Optional<Medicine> findByDosageAndAndDosageTypeAndName(final Integer dosage, final DosageTypeEnum dosageType, final String name);
}
