# To develop

Download the latest release of [ModTheSpire](https://github.com/kiooeht/ModTheSpire/releases)
and [BaseMod](https://github.com/daviscook477/BaseMod/releases). ModTheSpire should go directly
into the lib/ folder, and BaseMod should be added to lib/mods/, create it if it doesn't exist.

Copy the main jar from slay the spire into this lib folder as well, it's
named desktop-1.0.jar

# To build and run

```
# From root
mvn package
# Then cd into lib and run:
./run.sh
```
