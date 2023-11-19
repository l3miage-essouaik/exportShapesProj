package edu.uga.miage.m1.polygons.gui.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandInvoker {
    private Command command;

    public CommandInvoker(Command command) {
        this.command = command;
    }

    public void execute() {
        command.execute();
    }
}
