package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Medicine;
import br.com.pillwatcher.dpb.exceptions.MedicineException;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.mappers.MedicineMapper;
import br.com.pillwatcher.dpb.repositories.MedicineRepository;
import br.com.pillwatcher.dpb.services.MedicineService;
import io.swagger.model.ErrorCodeEnum;
import io.swagger.model.MedicineDTOForCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;
    private final MedicineMapper mapper;

    @Override
    @Transactional
    public Medicine create(final MedicineDTOForCreate medicineDto) {

        log.info("MedicineServiceImpl.create - Start - Input {}", medicineDto);

        /*Optional<Medicine> medicineFound = medicineRepository.findById();*/ //TODO refactor
        Optional<Medicine> medicineFound = Optional.empty();

        if (medicineFound.isPresent()) {
            log.warn(ValidationConstraints.MEDICINE_ALREADY_EXISTS, medicineDto.getName());
            throw new MedicineException(ErrorCodeEnum.UNEXPECTED_ERROR, ErrorMessages.CONFLICT,
                    StringUtils.replace(ValidationConstraints.MEDICINE_ALREADY_EXISTS, "{}", medicineDto.getName()));
        }

        Medicine medicine = mapper.dtoToEntity(medicineDto);

        return repository.save(medicine);
    }

    @Override
    @Transactional
    public Medicine getMedication(final Long medicineId) {

        Optional<Medicine> medicineOptional = repository.findById(medicineId);

        if (!medicineOptional.isPresent()) {
            log.warn(ValidationConstraints.MEDICINE_NOT_FOUND, medicineId);
            throw new PatientException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.MEDICINE_NOT_FOUND, "{}", medicineId.toString()));
        }

        return medicineOptional.get();
    }

    @Override
    @Transactional
    public void deleteMedicine(final Long medicineId) {

        log.info("MedicineServiceImpl.create - Start - Input {}", medicineId);

        /*Optional<Medicine> medicineFound = medicineRepository.findById();*/ //TODO refactor
        Optional<Medicine> medicineFound = Optional.empty();

        if (!medicineFound.isPresent()) {
            log.warn(ValidationConstraints.MEDICINE_NOT_FOUND, medicineId);
            throw new MedicineException(ErrorCodeEnum.UNEXPECTED_ERROR, ErrorMessages.CONFLICT,
                    StringUtils.replace(ValidationConstraints.MEDICINE_NOT_FOUND, "{}", String.valueOf(medicineId)));
        }

        repository.delete(medicineFound.get());
    }
}
