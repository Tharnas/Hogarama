package com.gepardec.hogarama.pi4j;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MoistureSensor {
  private static Logger logger = LogManager.getLogger(MoistureSensor.class);
  private static SpiDevice spi = null;
  static {
    try {
      spi = SpiFactory.getInstance(SpiChannel.CS0,
          SpiDevice.DEFAULT_SPI_SPEED, // default spi speed 1 MHz
          SpiDevice.DEFAULT_SPI_MODE);
    } catch (IOException e) {
      logger.fatal("Cannot initialise the SPI");
    }
  }

  /**
   * Read data via SPI bus from MCP3002 chip.
   * @throws IOException
   */
  public int readData() {
    try {
      return getConversionValue((short) 1);
    } catch (IOException e) {
      logger.error("Cannot read from MCP3008");
      return -1;
    }
  }


  /**
   * Communicate to the ADC chip via SPI to get single-ended conversion value for a specified channel.
   * @param channel analog input channel on ADC chip
   * @return conversion value for specified analog input channel
   * @throws IOException
   */
  public static int getConversionValue(short channel) throws IOException {

    // create a data buffer and initialize a conversion request payload
    byte data[] = new byte[] {
        (byte) 0b00000001,                              // first byte, start bit
        (byte)(0b10000000 |( ((channel & 7) << 4))),    // second byte transmitted -> (SGL/DIF = 1, D2=D1=D0=0)
        (byte) 0b00000000                               // third byte transmitted....don't care
    };

    // send conversion request to ADC chip via SPI channel
    byte[] result = spi.write(data);

    // calculate and return conversion value from result bytes
    int value = (result[1]<< 8) & 0b1100000000; //merge data[1] & data[2] to get 10-bit result
    value |=  (result[2] & 0xff);
    return value;
  }

}
