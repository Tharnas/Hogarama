package com.gepardec.hogarama.pi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PumpControl {
	private final GpioController gpio = GpioFactory.getInstance();
	private	final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
	private final int pumpDuration;

	public PumpControl(int pumpDuration) {
		this.pumpDuration = pumpDuration;
//		pin.setShutdownOptions(true, PinState.LOW);
	}

	public void start() {
		pin.high();

		try {
			Thread.sleep(pumpDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		pin.low();
	}
}