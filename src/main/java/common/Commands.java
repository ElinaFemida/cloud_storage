package common;

import java.io.Serializable;

public abstract class Commands implements Serializable {
    public enum Command {
        LOGIN,
        AUTH,
        FILE_TO_SERVER,
        FILE_FROM_SERVER,
        DELETE
    }

    public Command command;

    public Command getType() {
        return command;
    }

    public boolean isType (Command type) {
        return this.command.equals(type);
    }
}