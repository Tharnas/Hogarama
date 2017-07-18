package com.gepardec.hogarama.pi4j.mqtt;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PumpControl {
	private final GpioController gpio = GpioFactory.getInstance();
	private	final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
	{
		pin.setShutdownOptions(true, PinState.LOW);
	}

	public void pumpForDuration(int pumpDuration) {
		pin.high();
		System.out.println("pump activated");

		try {
			Thread.sleep(pumpDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pin.low();
		System.out.println("pump deactivated");
	}
}