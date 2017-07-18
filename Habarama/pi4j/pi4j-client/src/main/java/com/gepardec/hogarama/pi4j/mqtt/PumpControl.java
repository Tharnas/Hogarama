package com.gepardec.hogarama.pi4j.mqtt;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PumpControl {
	static final GpioController gpio = GpioFactory.getInstance();
	static final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Pump Control", PinState.LOW);
	static {
		pin.setShutdownOptions(true, PinState.LOW);
	}

	public void pumpForDuration(int pumpDuration) throws InterruptedException {
		pin.high();
		System.out.println("--> GPIO state should be: ON");

		Thread.sleep(pumpDuration);

		pin.low();
		System.out.println("--> GPIO state should be: OFF");

	}
}