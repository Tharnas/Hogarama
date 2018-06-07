# Actors

An actor is a component that is responsible for moving and controlling a mechanism or system. This could be used to control a water pump, blinds or maybe some lights.

Supported actors:
* GPIO *todo: replace with link*
* Console *todo: replace with link*

More actors will be supported.


## Host settings example

    "actors": [
      {
        "name": "Pump 1",
        "location": "Wien",
        "type": "gpio",
        "pin": 4
      },
      {
        "name": "Pump 2",
        "location": "Wien",
        "type": "gpio",
        "pin": 5
      },
      {
        "name": "Debugging",
        "location": "Wien",
        "type": "console"
      }
    ]

The properties `name`, `location` and `type` are always required. Depending on the type there could be more properties required.

More info: https://github.com/Gepardec/Hogarama/wiki/Raspberry