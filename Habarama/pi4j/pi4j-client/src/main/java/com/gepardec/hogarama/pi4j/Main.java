package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import com.gepardec.hogarama.pi4j.mqtt.PiMqttCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;


public class Main {
	public static MqttClient client = new MqttClientFactory().getClient(new PiMqttCallback());
	public static MqttTopic topic = client.getTopic(MqttClientFactory.AMQ_TOPIC);
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String args[]) {
		logger.info("System started ...");

		try {
			client.subscribe(MqttClientFactory.AMQ_TOPIC);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
