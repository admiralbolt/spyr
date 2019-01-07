# Attempt at modding slay the spire

Download the latest release of [ModTheSpire](https://github.com/kiooeht/ModTheSpire/releases)
and [BaseMod](https://github.com/daviscook477/BaseMod/releases). These go into
the lib folder (or check your pom file for where the dependencies are listed).

Copy the main jar from slay the spire into this lib folder as well, it's
named desktop-1.0.jar

# To build and run

```
# From root
mvn package
# Then cd into lib and run:
./run.sh
```
