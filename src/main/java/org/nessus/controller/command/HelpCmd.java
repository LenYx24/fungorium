package org.nessus.controller.command;

public class HelpCmd extends BaseCommand{
    @Override
    public void Run(String[] args) {
        String help = """
                Használat:
                [parancs neve] [paraméterek...]

                { }: kötelező paraméter
                [ ]: opcionális paraméter

                Normál Parancsok:
                exit
                help [parancsnév]
                
                Arrange parancsok:
                create {típus} {név} [tulajdonos]
                neighbour {tekton} {tekton}
                setref {célobjektum} {tulajdonság} {új érték}
                setval {célobjektum} {változó} {új érték}
                place {objektum} {tekton}
                placethread {gombafonal} {tekton} {tekton}
                seed {érték}
                currentplayer {játékos}
                addeffect {rovar} {effekt}
                run {teszt}

                Act parancsok:
                move {rovar} {tekton}
                cutthread {rovar} {gombafonál}
                eatspore {rovar} {spóra}
                updatebugs {rovarász}
                placeshroomthread {tekton} {tekton}
                placeshroombody {tekton}
                upgradeshroombody {gombatest}
                throwspore {gombatest} {tekton}
                devour {gombafonál} {rovar}
                updateshroom {gombafaj}
                split {tekton}
                updatetecton {tekton}
                nextplayer
                
                Assert parancsok:
                show
                showall
                save
                
                """;
        switch(args[0])
        {
            case "exit":
            {
                System.out.println("exit: Paraméter nélkül hívható parancs, amely bezárja a programot.");
                break;
            }
            case "help":
            {
                System.out.println("help [parancsnév]: Paraméterként megadható egy másik parancs neve, a parancs használatát ismerteti. Paraméter nélkül általános ismertetőt ad.");
                break;
            }
            case "create":
            {
                System.out.println("create {típus} {név} [tulajdonos]: Létrehoz egy objektumot. Első paramétere az objektum típusa, második paramétere az objektum neve.");
                break;
            }
            case "neighbour":
            {
                System.out.println("neighbour {tekton} {tekton}: Két tektont vár paraméterként, szomszédságot hoz létre közöttük.");
                break;
            }
            case "setref":
            {
                System.out.println("setref {célobjektum} {tulajdonság} {új érték}: Egy objektum tulajdonásgát képes állítani. Első paramétere az objektum, második paramétere az objektum állítandó tulajdonsága, harmadik paramétere az új érték.");
                break;
            }
            case "setval":
            {
                System.out.println("setval {célobjektum} {változó} {új érték}: Egy objektum valamely véltozóját képes állítani. Első paramétere az objektum, második paramétere az objektum állítandó változója, harmadik paramétere az új érték.");
                break;
            }
            case "place":
            {
                System.out.println("place {objektum} {tekton}: A paraméter objektumot elhelyezi a paraméter tektonon.");
                break;
            }
            case "placethread":
            {
                System.out.println("placethread {gombafonal} {tekton} {tekton}: A paraméter gombafonalat a két paraméter tekton közé húzza.");
                break;
            }
            case "seed":
            {
                System.out.println("seed {érték}: A játékban a véletlenszerűséget szabályzó logika seed-je állítható vele.");
                break;
            }
            case "currentplayer":
            {
                System.out.println("currentplayer {játékos}: A paraméter játékost állítja aktívra, ő léphet.");
                break;
            }
            case "addeffect":
            {
                System.out.println("addeffect {rovar} {effekt}: A paraméter rovarra felhelyezi a paraméter effektet.");
                break;
            }
            case "run":
            {
                System.out.println("run {teszt}: Futtattja a paraméter tesztesetet.");
                break;
            }
            case "move":
            {
                System.out.println("move {rovar} {tekton}: A paraméter rovart a paraméter tektonra mozgatja.");
                break;
            }
            case "cutthread":
            {
                System.out.println("cutthread {rovar} {gombafonál}: A paraméter rovar elvágja a paraméter gombafonalat.");
                break;
            }
            case "eatspore":
            {
                System.out.println("eatspore {rovar} {spóra}: A paraméter rovar megeszi a paraméter spórát.");
                break;
            }
            case "updatebugs":
            {
                System.out.println("updatebugs {rovarász}: Frissíti a paraméter rovarász rovarjait.");
                break;
            }
            case "placeshroomthread":
            {
                System.out.println("placeshroomthread {tekton} {tekton}: Új gombafonalat húz a két paraméter tekton közé.");
                break;
            }
            case "placeshroombody":
            {
                System.out.println("placeshroombody {tekton}: Új gombatestet növeszt a paraméter tektonon.");
                break;
            }
            case "upgradeshroombody":
            {
                System.out.println("upgradeshroombody {gombatest}: Egy szinttel felfejleszti a paraméter gombatestet.");
                break;
            }
            case "throwspore":
            {
                System.out.println("throwspore {gombatest} {tekton}: A paraméter gombatest spórát lő a paraméter tektonra.");
                break;
            }
            case "devour":
            {
                System.out.println("devour {gombafonál} {rovar}: A paraméter gombafonál felfalja a paraméter rovart.");
                break;
            }
            case "updateshroom":
            {
                System.out.println("updateshroom {gombafaj}: Frissíti a paraméter gombafaj elemeit.");
                break;
            }
            case "split":
            {
                System.out.println("split {tekton}: Kettétöri a paraméter tektont.");
                break;
            }
            case "updatetecton":
            {
                System.out.println("updatetecton {tekton}: Frissíti a paraméter tektont.");
                break;
            }
            case "nextplayer":
            {
                System.out.println("nextplayer: A soron következő játékosra vált.");
                break;
            }
            default: System.out.println(help);
        }
    }
}
