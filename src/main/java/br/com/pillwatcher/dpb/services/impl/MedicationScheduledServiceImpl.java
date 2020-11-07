package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.entities.MqttMedication;
import br.com.pillwatcher.dpb.exceptions.MedicationException;
import br.com.pillwatcher.dpb.mappers.MedicationMapper;
import br.com.pillwatcher.dpb.repositories.AppliedMedicationRepository;
import br.com.pillwatcher.dpb.repositories.MedicationRepository;
import br.com.pillwatcher.dpb.services.MedicationScheduledService;
import br.com.pillwatcher.dpb.services.MqttService;
import io.swagger.model.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicationScheduledServiceImpl implements MedicationScheduledService {

    private final MedicationMapper medicationMapper;

    private final MqttService mqttService;

    private final MedicationRepository medicationRepository;

    private final AppliedMedicationRepository appliedMedicationRepository;

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void checkMedication() {

        LocalDateTime now = LocalDateTime.now();
        log.info("MedicationScheduledServiceImpl.checkMedication - " +
                " Starting medication check {}", now);

        final List<MqttMedication> medicationsToApplyFirst = medicationRepository
                .findMedicationsToApplyFirst(now.withNano(0).withSecond(0),
                        now.plusMinutes(1).withNano(0).withSecond(0));

        final List<MqttMedication> medicationsToApply = medicationRepository
                .findMedicationsToApply(now.withNano(0).withSecond(0),
                        now.plusMinutes(1).withNano(0).withSecond(0));

        List<MqttMedication> result = Stream.concat(medicationsToApplyFirst.stream(), medicationsToApply.stream()).collect(Collectors.toList());

        result.forEach(mqttMedication -> {
            mqttService.setupMqtt(mqttMedication, "apply-medication",
                    new MedicationException(
                            ErrorCodeEnum.INVALID_PARAMETER,
                            ErrorMessages.BAD_REQUEST,
                            "Failed to start chron to apply medication"));

            final LocalDateTime nextMedication = now.plusHours(mqttMedication.getIntervalTime());
            appliedMedicationRepository.save(medicationMapper.mqttToMedication(mqttMedication, now, nextMedication));
        });
    }

}
