# Example client for connection between PI and Mqtt in OpenShift

## Prerequisites
* Python 2.7 or 3.x
* Git
* Paho Mqtt Python develop branch
* Raspberry Pi with sensor

### Python 2.7 or 3.x
To install python follow instuction on https://www.python.org/about/.

### Git
Download your favorite git client :)

### Paho Mqtt Python develop branch
> Support for SNI header in Paho Mqtt Pathon in not yet merged in master branch.
```
git clone https://github.com/eclipse/paho.mqtt.python.git
cd paho.mqtt.python
git checkout develop
python setup.py install
```

### Raspberry Pi with sensor
You need to have following hardware:
* Raspberry Pi
* Power source
* Breadboard
* Wires to connect everything 
* A sensor. It's up to you what for a sensor you try. In this example we use moisture sensor with analog outpu.
* Analog digital converter. Raspberry Pi GPIO pins can process only digital input. To convert analog data to digital we need a converter. We have choosen a MCP3008 with Software SPI.

## Assembly
The most complicated assembly part is analog digital converter. You can read more about how to connect it to raspberry [here](https://learn.adafruit.com/raspberry-pi-analog-to-digital-converters/mcp3008).
 

