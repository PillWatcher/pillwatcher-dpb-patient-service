package br.com.pillwatcher.dpb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MqttMedication {

    private Long medicationId;
    private Long nurseId;
    private Long cupId;
    private Integer location;
    private Integer intervalTime;

}
