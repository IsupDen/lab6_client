package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static util.TextFormat.errText;
import static util.TextFormat.successText;

public class CommandManager {
    private final CommandReader commandReader;
    private final Validator validator;
    private final Console console;
    private final Set<String> usedScripts;
    private final LabFactory labFactory;


    public CommandManager(CommandReader commandReader) {
        this.commandReader = commandReader;
        validator = Validator.getInstance();
        console = Console.getInstance();
        usedScripts = new HashSet<>();
        labFactory = new LabFactory();
    }


    public void transferCommand(Request command) {

        if (validator.notObjectArgumentCommands(command))
            console.println(RequestHandler.getInstance().send(command));
        else if (validator.objectArgumentCommands(command)) {
            console.println(RequestHandler.getInstance().send(command, labFactory.createLabWork()));
        } else if (validator.validateScriptArgumentCommand(command)) {
            executeScript(command.getArg());
        } else {
            console.println(errText("Command entered incorrectly!"));
        }
    }

    private void executeScript(String scriptName) {

        if (usedScripts.add(scriptName)) {

            try {

                if (usedScripts.size() == 1) console.setExeStatus(true);

                ScriptReader scriptReader = new ScriptReader(this, commandReader, new File(scriptName));
                try {
                    scriptReader.read();

                    console.println(successText("The script " + scriptName
                            + " was processed successfully!"));
                } catch (IOException exception) {

                    usedScripts.remove(scriptName);

                    if (usedScripts.size() == 0) console.setExeStatus(false);

                    if (!new File(scriptName).exists()) console.println(
                            errText("The script does not exist!"));
                    else if (!new File(scriptName).canRead()) console.println(
                            errText("The system does not have permission to read the file!"));
                    else console.println("We have some problem's with script!");
                }

                usedScripts.remove(scriptName);

                if (usedScripts.size() == 0) console.setExeStatus(false);

            } catch (FileNotFoundException e) {
                console.println("Script not found!");
            }
        } else console.println(errText("Recursion has been detected! Script " + scriptName +
                " will not be ran!"));
    }
}
