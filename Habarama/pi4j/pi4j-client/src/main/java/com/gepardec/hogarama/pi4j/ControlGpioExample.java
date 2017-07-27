package com.gepardec.hogarama.pi4j;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * This example code demonstrates how to perform simple state
 * control of a GPIO pin on the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class ControlGpioExample {
  public static void main(String[] args) {
	  try {
		  startGpioExample();
	  } catch (InterruptedException e) {
		  e.printStackTrace();
	  }
  }

  public static void startGpioExample() throws InterruptedException {

    System.out.println("<--Pi4J--> GPIO Control Example ... started.");

    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();

    // provision gpio pin #01 as an output pin and turn on
    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    // set shutdown state for this pin

    pin.setShutdownOptions(true, PinState.LOW);

    for(int i=0;; i++) {

      pin.high();
      System.out.println("--> GPIO state should be: ON");

      Thread.sleep(5000);

      pin.low();
      System.out.println("--> GPIO state should be: OFF");

      Thread.sleep(1000);

    }
    //gpio.shutdown(); //error cause it's not reachable after the infinite loop (says the compiler :-))

  }
}