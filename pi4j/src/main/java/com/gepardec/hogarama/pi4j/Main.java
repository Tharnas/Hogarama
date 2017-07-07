package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.MqttClientFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;


public class Main {

  public static void main(String args[]) {
    
	  System.out.println("Hello World!");

		MqttClient client = new MqttClientFactory().getClient();

		boolean subscriber = true;
		boolean publisher = false;

		String myTopic = MqttClientFactory.AMQ_TOPIC;
		MqttTopic topic = client.getTopic(myTopic);


		int mqttPackageRepeat = 1;

		// subscribe to topic if subscriber
		if (subscriber) {
			try {
				int subQoS = 0;
				client.subscribe(myTopic, subQoS);
				System.out.println("Subscribed to topic " + myTopic);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// publish messages if publisher
		if (publisher) {
			for (int i = 1; i <= mqttPackageRepeat; i++) {
//				String pubMsg = "{\"pubmsg\":" + i + "}";
//				String pubMsg = //"{"+
//							//"'habarama':"+
//								"{"+
//								"\"habarama_id\":"+"\""+clientID+"\"," +
//								"\"sensor_data\":"+"{"
//									+"\"temp\":"+27.2+","
//									+"\"comment\":"+"\"Too hot!\""
//								+"}"
////							+"}"
//						+"}";
				String pubMsg = "{\"pubmsg\": 1.5 }";
				int pubQoS = 0;
				MqttMessage message = new MqttMessage(pubMsg.getBytes());
				message.setQos(pubQoS);
				message.setRetained(false);

				// Publish the message
				System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
				MqttDeliveryToken token = null;
				try {
					// publish message to broker
					token = topic.publish(message);
					// Wait until the message has been delivered to the broker
					token.waitForCompletion();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		while (true) {
			try {
				if (subscriber) {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//
//	  try {
//		  ControlGpioExample.startGpioExample();
//	  } catch (InterruptedException e) {
//		  e.printStackTrace();
//	  }
  }


}
