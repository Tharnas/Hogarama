package com.gepardec.hogarama.pi4j.mqtt;

import com.gepardec.hogarama.pi4j.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class PiMqttCallback implements MqttCallback {
  private static Logger logger = LogManager.getLogger(PiMqttCallback.class);
  private PumpControl pump = new PumpControl();

  public void connectionLost(Throwable t) {
    logger.error("Connection lost!");
    try {
      logger.info("Attempt to reconnect to server...");
      Main.client.reconnect();
      logger.info("Reconnection successful.");
    } catch (MqttException e) {
      logger.error("Reconnection failed.");
    }
  }

  public void deliveryComplete(IMqttDeliveryToken token) { }

  public void messageArrived(String topic, MqttMessage message) throws Exception {
    logger.debug("Message received on " + topic + ": " + message.toString());
    if (topic.equals(Main.topic.getName())) {
      try {
        int pumpDuration = Integer.parseInt(new String(message.getPayload()).trim());
        pump.pumpForDuration(pumpDuration);
      } catch (NumberFormatException e) {
        logger.error("Payload cannot be converted to integer.");
      }

    }
  }

}
