package util;

import data.LabWork;

import java.io.Serializable;

public class Request implements Serializable {

    private final String command;
    private final String arg;
    private LabWork labWork;

    public Request(String command, String arg) {
        this.command = command;
        this.arg = arg;
        labWork = null;
    }

    public void addLabWork(LabWork labWork) {
        this.labWork = labWork;
    }

    public String getCommand() {return command;}

    public String getArg() {return arg;}

    public LabWork getLabWork() {return labWork;}

    public boolean isArgInt() {
        try {
            if (arg != null) {
                Integer.parseInt(arg);
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return command + " "
                + (arg != null ? arg : "")
                + (labWork != null ? labWork : "");
    }


}
