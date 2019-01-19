#!/bin/bash

java -jar cfr_0_139.jar \
  --comments true \
  --showversion false \
  --caseinsensitivefs true \
  --outputdir ../../lib/decompiled \
  --jarfilter com.megacrit.cardcrawl.* \
  ../../lib/desktop-1.0.jar

cp ../../lib/desktop-1.0.jar ../../lib/decompiled/
cd ../../lib/decompiled/
unzip desktop-1.0.jar
