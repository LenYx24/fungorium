# Projekt

A BME VIK 4. féléves szoftver projekt laboratórium tárgy "szkeleton elkészítés" fázishoz készült projekt feladat.

A projekt jelenlegi állapota:
[![Java CI with Maven (SzoftProjLab)](https://github.com/LenYx24/fungorium-skeleton/actions/workflows/maven.yml/badge.svg)](https://github.com/LenYx24/fungorium-skeleton/actions/workflows/maven.yml)

## Követelmények

java verzió: java 23.0.2 2025-01-21\
A maven build szoftver lokálisan elérhető, nem kell külön telepíteni.

### JAVA_HOME beállítása.

Először keresse meg, hogy hova van feltelepítve a java JDK.\
Windowson általában itt szokott lenni (a jdk_version-t cserélje ki a verzióra amit szeretne használni): "C:\Program
Files\Java\jdk_version\bin"\
Ezután írja a Windows keresőbe, hogy "environment variables" (vagy magyarul "környezeti változók").\
Kattintson az jobb alsó "Environment Variables..." gombra, majd a felső listában a PATH nevű sorra kattintson kétszer.\
Ekkor megjelenik egy új lista, ahova a "New" gomba segítségével adja hozzá az útvonalat.\
Ezután mentse el a változtatásokat az "Ok" és "Apply" gombokkal.\
A terminálokat újra kell indítani, hogy érvényesüljenek a változtatások.

## Futtatás

Először nyisson egy terminált, és navigáljon a projekt főkönyvtárába (fungorium-skeleton).\
Ezután adja ki a következő parancsot, amellyel a maven lefordítja a kódot és készít egy jar fájlt a target könyvtárban:

```
.\mvnw package
```

Lehet linuxon előbb a "chmod +x mvnw" parancsot ki kell adni.\
Ezután futtassa a jar fájlt:

```
java -jar .\target\fungorium-skeleton-1.0.jar
```

## Tipikus hibák:

Ha linuxon ezt az errort kapod: "-bash: ./mvnw: /bin/sh^M: bad interpreter: No such file or directory",\
akkor lehet a line endings-el van baj, futtasd ezt a parancsot: "sed -i 's/\r$//' mvnw"
