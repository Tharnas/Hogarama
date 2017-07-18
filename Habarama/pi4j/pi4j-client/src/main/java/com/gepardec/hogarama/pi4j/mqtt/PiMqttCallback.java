package com.gepardec.hogarama.pi4j.mqtt;

import com.gepardec.hogarama.pi4j.Main;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class PiMqttCallback implements MqttCallback {
  private PumpControl pump = new PumpControl();

  public void connectionLost(Throwable t) {
    System.out.println("Connection lost!");
    t.printStackTrace();
    // TODO: reconnect
  }

  public void deliveryComplete(IMqttDeliveryToken token) { }

  public void messageArrived(String topic, MqttMessage message) throws Exception {
    System.out.println("Message received on " + topic + ": " + message.toString());
    if (topic.equals(Main.topic.getName())) {
      int pumpDuration = Integer.parseInt(new String(message.getPayload()).trim());

      pump.pumpForDuration(pumpDuration);
    }
  }

}
