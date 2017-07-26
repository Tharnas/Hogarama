package com.gepardec.hogarama.pi4j.mqtt;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PumpControl {
	private static Logger logger = LogManager.getLogger(PumpControl.class);
	private static final GpioController gpio = GpioFactory.getInstance();
	private static final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Pump Control", PinState.LOW);
	static {
		pin.setShutdownOptions(true, PinState.LOW);
	}

	public void pumpForDuration(int pumpDuration) throws InterruptedException {
		pin.high();
		logger.debug("Start pumping.");

		Thread.sleep(pumpDuration);

		pin.low();
		logger.debug("Stop pumping.");
	}
}