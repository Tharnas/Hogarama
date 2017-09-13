package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import com.gepardec.hogarama.pi4j.mqtt.PiMqttCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;


public class Main {
	public static final String PUMP_TOPIC = "pump_control";
	public static final String SENSOR_TOPIC = "habarama";

	public final static MqttClient CLIENT = new MqttClientFactory().getClient(new PiMqttCallback());
	public static MqttTopic sensor_topic = CLIENT.getTopic(SENSOR_TOPIC);
	private final static Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String args[]) {
		LOGGER.info("System started ...");

		try {
			CLIENT.subscribe(PUMP_TOPIC);
		} catch (MqttException e) {
			e.printStackTrace();
		}

		MoistureSensor sensor = new MoistureSensor();

		while (true) {
			sendMoisture(sensor.readData());

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				LOGGER.error("Threading problem");
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

		LOGGER.debug("Publishing to habarama: " + payload);
		try {
			MqttDeliveryToken token = sensor_topic.publish(message);
			token.waitForCompletion();
		} catch (MqttException e) {
			System.out.println("Failed to send the message to server.");
		}
	}
}
