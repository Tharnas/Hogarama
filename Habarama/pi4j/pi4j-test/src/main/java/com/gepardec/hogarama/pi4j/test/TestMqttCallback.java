package com.gepardec.hogarama.pi4j.test;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class TestMqttCallback implements MqttCallback {

  public void connectionLost(Throwable t) {
    System.out.println("Connection lost!");
    // TODO: reconnect
  }

  public void deliveryComplete(IMqttDeliveryToken token) {
    System.out.println(token.toString());
  }

  public void messageArrived(String topic, MqttMessage message) throws Exception {

  }

}
