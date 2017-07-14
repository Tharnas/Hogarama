package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import com.gepardec.hogarama.pi4j.mqtt.PiMqttCallback;
import org.eclipse.paho.client.mqttv3.*;


public class Main {
	private static MqttClient client = new MqttClientFactory().getClient(new PiMqttCallback());
	public static MqttTopic topic = client.getTopic(MqttClientFactory.AMQ_TOPIC);


	public static void main(String args[]) {
		System.out.println("System started ...");
		try {
			client.subscribe(MqttClientFactory.AMQ_TOPIC);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
