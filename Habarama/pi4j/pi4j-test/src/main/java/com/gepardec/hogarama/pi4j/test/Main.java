package com.gepardec.hogarama.pi4j.test;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import org.eclipse.paho.client.mqttv3.*;


public class Main {
	private static MqttClient client = new MqttClientFactory().getClient(new TestMqttCallback());
	public static MqttTopic topic = client.getTopic(MqttClientFactory.AMQ_TOPIC);


	public static void main(String args[]) {

		sendMessage("3000");

		try {
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

  private static void sendMessage(String pumpDuration) {
		int pubQoS = 0;
		MqttMessage message = new MqttMessage(pumpDuration.getBytes());
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
