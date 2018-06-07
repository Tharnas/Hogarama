...

## Hogajama message format

### Sensor data

This data is sent from the Raspberry Pi to the cloud.

`MQTT Topic`: habarama

    {
      "sensorName": <sensorName>,
      "type": <sensorType>,
      "value": <value>,
      "location": <location>,
      "version": <version>
    }


### Actor command

This command is sent from the cloud to the Raspberry Pi

`MQTT Topic`: actor.&lt;ActorLocation&gt;.&lt;ActorName&gt;

    {
       "name": "<ActorName>",
       "location": "<ActorLocation>",
       "duration": duration in seconds
    }

...

## Configuration

...

### habarama.json format

    {
      "brokerUrls": ["broker1", "broker2"],
      "sensors": [
        {
          "name": "<sensor.name>",
          "type": "<sensor.type>",
          "location": "<sensor.location>",
          "channel": <sensor.channel>,
          "pin": <sensor.pin>
        }
      ],
      "actors": [
        {
          "name": "<actor.name>",
          "location": "<actor.location>",
          "type": "<actor.type>",
          "pin": "<actor.pin>"
        }
      ]
    }


* `brokerUrls`: Array of MQTT broker URLs.
* `sensors`: Array of sensors connected to Raspberry Pi.
* `sensors.name`: Unique name for sensor for given location and type. Type: String
* `sensors.type`: Describes which kind of values the sensor reads. Type: String
* `sensors.location`: Where is sensor located. Type: String
* `sensors.channel`: The channel on MCP3008 chip. Type: Number
* `sensors.pin`: To which pin is the sensor connected. Type: Number
* `actors`: Array of actors connected to Raspberry Pi.
* `actors.name`: Unique name for actor for given location. Type: String
* `actors.location`: Where the actor is located. Type:String
* `actors.type`: Describes the kind of actor. Currntly possible values are supported: gpio | console. Type: String
* `actors.pin`: To which pin the actor is connected This is only required in case `actors.type` is `gpio`. Type: Number