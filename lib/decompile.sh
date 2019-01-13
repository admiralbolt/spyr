#!/bin/bash

java -jar cfr_0_139.jar \
  --comments true \
  --showversion false \
  --caseinsensitivefs true \
  --outputdir decompiled \
  --jarfilter com.megacrit.cardcrawl.* \
  desktop-1.0.jar

cp desktop-1.0.jar decompiled/
cd decompiled/
unzip desktop-1.0.jar
