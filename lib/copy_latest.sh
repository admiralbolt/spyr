#!/bin/bash
MAC_BASEPATH=~/Library/Application\ Support/Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents/Resources/
LINUX_BASEPATH=~/.steam/steam/steamapps/common/SlayTheSpire/
if [ -d "${MAC_BASEPATH}" ];
then
  BASEPATH=$MAC_BASEPATH
else
  BASEPATH=$LINUX_BASEPATH
fi

cp "${BASEPATH}/desktop-1.0.jar" .
cp -r "${BASEPATH}/preferences" .
