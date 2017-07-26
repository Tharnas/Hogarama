package com.gepardec.hogarama.pi4j.test;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class TestMqttCallback implements MqttCallback {

  public void connectionLost(Throwable t) {
    System.out.println("Connection lost!");
    // TODO: reconnect
  }

  public void deliveryComplete(IMqttDeliveryToken token) {
    try {
      System.out.println(new String(token.getMessage().getPayload()) + " delivered");
    } catch (MqttException e) {
      System.out.println("Delivery failed");
    }
  }

  public void messageArrived(String topic, MqttMessage message) throws Exception {

  }

}
