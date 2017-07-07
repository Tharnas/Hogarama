package com.gepardec.hogarama.pi4j.mqtt;

import com.gepardec.hogarama.pi4j.Main;
import com.gepardec.hogarama.pi4j.PumpControl;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class PiMqttCallback implements MqttCallback {
  private PumpControl pump = new PumpControl(1000);

  public void connectionLost(Throwable t) {
    System.out.println("Connection lost!");
    // TODO: reconnect
  }

  public void deliveryComplete(IMqttDeliveryToken token) {
    System.out.println(token.toString());
  }

  public void messageArrived(String topic, MqttMessage message) throws Exception {
    if (new String(message.getPayload()).equals(Main.CONTROL_MESSAGE) && topic.equals(Main.topic.getName())) {
      System.out.println("Start pumping.");
      pump.start();
      System.out.println("Pump stopped.");
    }
  }

}
