# OUCH - Offline Universal Converter Helper
A tool to convert between many different number systems and encodings.

## Installation
* Debian - [free-your-pc.com repository ](http://free-your-pc.com/software)

## How to use OUCH
If you have installed OUCH you can use it with the following commands:
```bash
ouch -h # display help
ouch -i plain -o morse "hello world"
ouch -s 10 -d 16 "12345678"
ouch -s 10 -d 2 --metrics "12345678"
```

## The OUCH GUI
The OUCH GUI is currently only available with the Maven task (please
see Testing).

### Testing
If you have installed the Java JDK and Maven you can test the CLI and
the GUI by doing:
```bash
mvn test -Pcli
mvn test -Pgui
```

## Dependencies
* args4j (CLI)
* JavaFX (GUI)

## Distribution packages
Debian packages are created automatically with the "package" goal. You
can sign your packages (this is default). To sign them look up the
"key" and "passphrase" tags in the pom.xml and adjust them to your
preferences.
