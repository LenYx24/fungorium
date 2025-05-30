# Fungorium monorepo
A BME VIK 4. féléves szoftver projekt laboratórium tárgy összes fázisát egybefogó monorepo.

Használjátok egészséggel mintaként 😉

A grafikus projekt jelenlegi állapota:

[![Java CI with Maven (SzoftProjLab)](https://github.com/LenYx24/fungorium-graphical/actions/workflows/maven.yml/badge.svg)](https://github.com/LenYx24/fungorium-graphical/actions/workflows/maven.yml)

Projektre kapott pontok:

| Szakasz neve | Kapott pontszám |
| ----------- | --------------- |
| Követelmény, projekt, funkcionalitás | 5/10 |
| Analízis modell (I. változat) | 10/20 |
| Analízis modell (II. változat) | 28/30 |
| Szkeleton tervezése | 17/20 |
| Szkeleton elkészítése | 20/20 |
| Prototípus koncepciója | 20/20 |
| Részletes tervek | 40/45 |
| Prototípus elkészítése | 35/35 |
| Grafikus változat tervei | 29/30 |
| Grafikus változat elkészítése | 40/40 |
| Egyesített dokumentáció | 30/30 |
| Összes pont | 91,3/100 (5) |

> [!TIP]
> A projekthez készített dokumentációt feltöltöttük a repo gyökérmappájába, a link a következő:
> [Dokumentáció](https://github.com/LenYx24/fungorium/blob/master/vegso%20redacted.pdf)
> A dokumentációban találhatóak a projekt részletes leírásai, a tervezési és fejlesztési folyamatok, valamint a tesztelési eredmények. Használjátok egészséggel, sok szerencsét kívánunk a Ti projektetekhez is, reméljük, hogy hasznosnak találjátok!
> Teszteléshez a dokumentáció 257. oldalán találhatjátok meg a fordítási és futtatási instrukciókat.

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
