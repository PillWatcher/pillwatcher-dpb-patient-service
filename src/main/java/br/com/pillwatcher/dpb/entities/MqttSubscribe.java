package br.com.pillwatcher.dpb.entities;

import lombok.Data;

@Data
public class MqttSubscribe {

    private String message;

    private Integer qos;

    private Integer id;

}
