#!/usr/bin/bash

HOST=pi     # SSH URL for Raspberry Pi
DIR=pi4j

ssh pi "mkdir -p $DIR"
ssh pi "rm -rf $DIR/*"
scp $1 $HOST:$DIR  # copy the jar
scp -r $2 $HOST:$DIR # copy lib/*
