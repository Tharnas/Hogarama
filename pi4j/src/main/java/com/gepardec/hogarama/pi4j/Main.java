package com.gepardec.hogarama.pi4j;

import com.gepardec.hogarama.pi4j.mqtt.SimpleMqttClient;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


public class Main {

  public static void main(String args[]) {
    
	  System.out.println("Hello World!");

		SimpleMqttClient client = new SimpleMqttClient();
		client.runClient();
//
//	  try {
//		  ControlGpioExample.startGpioExample();
//	  } catch (InterruptedException e) {
//		  e.printStackTrace();
//	  }
  }


}
