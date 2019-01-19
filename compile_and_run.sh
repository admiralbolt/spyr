#!/bin/bash
if mvn package;
then
  cd ../lib/
  java -jar ModTheSpire.jar &> mod.log
fi
