package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.entities.MqttPublish;
import br.com.pillwatcher.dpb.services.MqttService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MqttServiceImpl implements MqttService {

    @Override
    public MqttMessage createMqttMessage(final MqttPublish publishModel) throws MqttException {

        log.info("MqttServiceImpl.publishMessage() - Start");
        log.debug("MqttServiceImpl.publishMessage() - Start - {}", publishModel);

        MqttMessage mqttMessage = new MqttMessage(publishModel.getMessage().getBytes());

        mqttMessage.setQos(publishModel.getQos());
        mqttMessage.setRetained(publishModel.getRetained());

        return mqttMessage;
    }
}
