package org.nessus.command;

public class HelpCmd extends BaseCommand{
    @Override
    public void Run(String[] args) {
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
