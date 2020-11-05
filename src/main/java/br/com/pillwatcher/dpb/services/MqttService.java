package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.MqttPublish;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttService {

    MqttMessage createMqttMessage(MqttPublish publishModel) throws MqttException;

}
