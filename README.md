# Projekt

A BME VIK 4. féléves szoftver projekt laboratórium tárgy "szkeleton elkészítés" fázishoz készült projekt feladat.

## Követelmények

java verzió: java 23.0.2 2025-01-21\
A maven build szoftver lokálisan elérhető, nem kell külön telepíteni.

## Futtatás

Először nyisson egy terminált, és navigáljon a projekt főkönyvtárába (fungorium-skeleton).\
Ezután adja ki a következő parancsot, amellyel a maven lefordítja a kódot és készít egy jar fájlt a target könyvtárban:

```
mvnw package
```

Lehet linuxon előbb a "chmod +x mvnw" parancsot ki kell adni.\
Ezután futtassa a jar fájlt:

```
java -jar .\target\fungorium-skeleton-1.0.jar
```