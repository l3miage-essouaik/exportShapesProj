package edu.uga.miage.m1.polygons.gui.command;

public class CommandInvoker {
    private Command command ;

    public CommandInvoker(Command command){
        this.command=command;
    }
    public void undo(){
        command.execute();
    }
}
