package com.asserta.gameoflife;

import java.io.IOException;

public class DangerousExec {
    public void runCommand(String userInput) throws IOException {
        Runtime.getRuntime().exec("cmd /c " + userInput);
    }
}
