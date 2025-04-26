package org.nessus.controller.command;

public class HelpCmd extends BaseCommand{
    @Override
    public void Run(String[] args) {
        // args[0]
        //      ˇ
        // help asd
        String help = """
                Használat:
                [parancs neve] [paraméterek...]

                Normál Parancsok:
                exit
                help
                
                Arrange parancsok:
                create

                Act parancsok:
                
                Assert parancsok:
                
                """;
        System.out.println(help);
    }
}
