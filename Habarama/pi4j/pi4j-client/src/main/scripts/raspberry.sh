#!/usr/bin/bash

HOST=pi     # SSH URL for Raspberry Pi
DIR=pi4j

ssh pi "mkdir -p $DIR"
ssh pi "rm -rf $DIR/* paho*"
scp $1 $HOST:$DIR  # copy the jar
scp -r $2 $HOST:$DIR # copy lib/*

#ssh pi "killall java"
#ssh pi "nohup java -jar pi4j/hogarama-pi4j.jar &"