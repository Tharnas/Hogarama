# Pi4J for Hogarama

Testing Pi4J on Raspberry Pi 3B for the [Hogaram Project](https://github.com/Gepardec/Hogarama)

## HOWTO:

1. Clone this repository
2. Build with `mvn clean package`
3. Copy `pi4j-test.jar` onto Raspberry Pi

### Start the pump

1. Run `java -jar pi4j-test.jar time_in_milliseconds`


### Check the moisture sensor

1. Run `java -cp hogarama-pi4j.jar com.gepardec.hogarama.pi4j.MoistureSensor`

The first two columns are the digital (LOW is moist, HIGH is dry) and analog outputs.




