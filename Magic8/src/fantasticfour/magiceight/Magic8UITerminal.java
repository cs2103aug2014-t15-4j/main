package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import fantasticfour.magiceight.Magic8Parser;
import fantasticfour.magiceight.command.CommandInvoker;

public class Magic8UITerminal {
    
    private static String cmd, filename;
    private static Magic8TaskList taskListManager = null;
    private static Magic8CommandObject cmdObj = new Magic8CommandObject();    
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static CommandInvoker invoker;
    
    private static void invoke() throws IOException {
        invoker = new CommandInvoker(cmdObj, taskListManager);
    }
    
    private static void magic8UIInit() throws IOException, ParseException {        
        System.out.print("Filename: ");
        filename = br.readLine();        
        taskListManager = new Magic8TaskList(filename);
    }
    
    private static void magic8UIRun() throws IOException {
        while(cmdObj.getFunction() != "quit") {
            System.out.print("Command > ");
            cmd = br.readLine();
            cmdObj = Magic8Parser.parseCommand(cmd);
            invoke();
        }
    }
 
    public static void main(String[] args) {   
        try {
            magic8UIInit();
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        
        try {
            magic8UIRun();
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }
}
