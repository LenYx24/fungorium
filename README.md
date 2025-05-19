# Projekt

A BME VIK 4. féléves szoftver projekt laboratórium tárgy "grafikus változat elkészítés" fázishoz készült projekt feladat.

A projekt jelenlegi állapota:

[![Java CI with Maven (SzoftProjLab)](https://github.com/LenYx24/fungorium-graphical/actions/workflows/maven.yml/badge.svg)](https://github.com/LenYx24/fungorium-graphical/actions/workflows/maven.yml)

## Követelmények

Java verzió: java 20

A kari felhő által szabott követelményeket írjuk elő

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

Először nyisson egy terminált, és navigáljon a projekt főkönyvtárába (fungorium-prototype).\
Ezután adja ki a következő parancsot, amellyel a maven lefordítja a kódot és készít egy jar fájlt a target könyvtárban:

```
.\mvnw package
```

Lehet linuxon előbb a "chmod +x mvnw" parancsot ki kell adni.\
Ezután futtassa a jar fájlt:

```
java -jar .\target\fungorium-prototype-1.0.jar
```

## Tipikus hibák:

Ha linuxon ezt az errort kapod: "-bash: ./mvnw: /bin/sh^M: bad interpreter: No such file or directory",\
akkor lehet a line endings-el van baj, futtasd ezt a parancsot: "sed -i 's/\r$//' mvnw"

# A játék leírása, ismertetése

## Fungorium

Egy stratégiai ökoszisztéma-játék gombákról, rovarokról és egy különös bolygóról.

---

### 🪐 A világ

A **Fungorium** bolygón gombák és rovarok élnek, és a felszínét különböző alakú kéregdarabok, úgynevezett **tektonok** borítják. Ezek a tektonok egy puhább, viszkózus rétegen "úsznak", és közöttük keskeny rések húzódnak. Időnként ketté is törhetnek, és az így keletkező darabok egymástól függetlenül fejlődnek tovább.

### 🍄 Gombák

A gombák **gombafonalakból** és **gombatestekből** állnak:

- Egy tektonon egyszerre csak egy gombatest nőhet.
- A gombatestek idővel spórákat szórhatnak a szomszédos tektonokra (vagy fejlettebb esetekben még távolabbra is).
- A gombafonalak elágazhatnak, átnőhetnek a réseken, és képesek új gombatesteket növeszteni elegendő spóra jelenlétében.
- Egy gombatest csak véges számú alkalommal tud spórát szórni, utána elpusztul.

### 🐜 Rovarok

A rovarok:

- A gombafonalakat követve mozognak, önállóan nem képesek átkelni a réseken.
- A spórákból táplálkoznak, amelyek eltérő hatást gyakorolnak rájuk:
  - Gyorsító, lassító, bénító, fonalvágást gátló stb. hatások.
- Egyes rovarok el tudják vágni a fonalakat a tektonok szélén.

### 🌋 Tektonok

A tektonok változatosak:

- Egyeseken több gombafonal is nőhet, máshol csak egy, vagy épp gombatest nem fejlődhet.
- Bizonyos tektonokon a fonalak idővel eltűnnek.
- A tekton kettétörése megszakítja a rajta levő fonalakat.

### 🧠 Játékosok szerepe

Két fő játékostípus létezik:

### Gombászok

A gombák terjedését irányítják:

- Meghatározzák a fonalak növekedésének irányát.
- Indítják a spóraszórást.
- Dönthetnek új gombatestek növesztéséről.

### Rovarászok

A rovarok mozgását és viselkedését befolyásolják:

- Irányítják a rovarokat.
- Eldönthetik, hogy egy rovar elvág-e egy adott fonalat.

### 🧬 Speciális mechanikák

- Egyes spórák **osztódásra kényszerítik** a rovarokat, így új, önálló példány jön létre ugyanazzal a rovarásszal.
- Létezik olyan **tekton-típus**, amely életben tartja a már elszakított fonalakat is.
- Az **elrágott fonalak** nem pusztulnak el azonnal – ez típusfüggő.
- A fonalak **fogyasztják a bénult rovarokat**, és ennek hatására gombatestet növeszthetnek.
