package com.gepardec.hogarama.pi4j.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by bxia on 07/07/17.
 */
public class PiMqttCallback implements MqttCallback {

  /**
   * connectionLost This callback is invoked upon losing the MQTT connection.
   */
  public void connectionLost(Throwable t) {
    System.out.println("Connection lost!");
    // TODO: reconnect
  }

  public void deliveryComplete(IMqttDeliveryToken token) {
    System.out.println(token.toString());
  }

  public void messageArrived(String topic, MqttMessage message) throws Exception {
    System.out.println("-------------------------------------------------");
    System.out.println("| Topic:" + topic);
    System.out.println("| Message: " + new String(message.getPayload()));
    System.out.println("-------------------------------------------------");
  }

}
