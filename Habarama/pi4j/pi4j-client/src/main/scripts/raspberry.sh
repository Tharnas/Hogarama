#!/usr/bin/bash

DIR=pi4j

ssh pi "mkdir -p $DIR"
ssh pi "rm -rf $DIR/*"
scp $1 pi:$DIR  # copy the jar
scp -r $2 pi:$DIR # copy lib/*
