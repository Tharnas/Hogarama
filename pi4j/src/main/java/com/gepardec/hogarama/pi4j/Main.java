package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;


public class Main {
	private static MqttClient client = new MqttClientFactory().getClient();
	public static final String CONTROL_MESSAGE = "lskjdfw9puq25klsbn klyh9325";
	public static MqttTopic topic = client.getTopic(MqttClientFactory.AMQ_TOPIC);
	static {
		client.getTopic(MqttClientFactory.AMQ_TOPIC);
	}

	public static void main(String args[]) {
		System.out.println("Hello World!");

		if (args[0] != null && args[0].equals("send")) {
			sendMessage();
		}
	}

  private static void sendMessage() {
		int pubQoS = 0;
		MqttMessage message = new MqttMessage(CONTROL_MESSAGE.getBytes());
		message.setQos(pubQoS);
		message.setRetained(false);

		System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
		MqttDeliveryToken token;
		try {
			token = topic.publish(message);
			token.waitForCompletion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
