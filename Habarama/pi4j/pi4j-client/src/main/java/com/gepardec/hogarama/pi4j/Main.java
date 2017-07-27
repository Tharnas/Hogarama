package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import com.gepardec.hogarama.pi4j.mqtt.PiMqttCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;


public class Main {
	public static final String PUMP_TOPIC = "pump_control";
	public static final String SENSOR_TOPIC = "habarama";

	public static MqttClient client = new MqttClientFactory().getClient(new PiMqttCallback());
	public static MqttTopic sensor_topic = client.getTopic(SENSOR_TOPIC);
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String args[]) {
		logger.info("System started ...");

		try {
			client.subscribe(PUMP_TOPIC);
		} catch (MqttException e) {
			e.printStackTrace();
		}

		MoistureSensor sensor = new MoistureSensor();

		while (true) {
			sendMoisture(sensor.readData());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Threading problem");
				e.printStackTrace();
			}
		}
	}

	private static void sendMoisture(int moisture) {
		String payload = "{{\"sensorName\": \"PlantInDistress\", \"type\": \"water\", \"value\": " + moisture + ", \"location\": \"Linz\", \"version\": 1 }}";

		int pubQoS = 0;
		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setQos(pubQoS);
		message.setRetained(false);

		logger.info("Publishing to habarama: " + payload);
		try {
			MqttDeliveryToken token = sensor_topic.publish(message);
			token.waitForCompletion();
		} catch (Exception e) {
			System.out.println("Failed to send the message to server.");
		}
	}
}
