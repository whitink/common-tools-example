package com.jline.example;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

import java.io.IOException;

/**
 * Hello world!
 */
public class JlineExample {
    public static void main(String[] args) throws IOException {
        System.setProperty("jline.internal.Log.debug", "true");
        System.setProperty("jline.WindowsTerminal.directConsole", "false");
        ConsoleReader reader = new ConsoleReader();
        Completer completer = new StringsCompleter(new String[]{"myabc",
                "mybcd", "testaaa", "tesss"});
        reader.addCompleter(completer);
        String line = null;
        do {
            line = reader.readLine(">");
            if (line != null && !line.equals("quit")) {
                System.out.println("show command : " + line);
            }
        } while (!line.equals("quit"));
    }
}
