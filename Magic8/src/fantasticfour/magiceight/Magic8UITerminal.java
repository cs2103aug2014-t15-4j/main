package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8Parser;

public class Magic8UITerminal {
    public static void main(String[] args) throws IOException {
        Magic8Parser.CommandObject cmdObj = new Magic8Parser.CommandObject();
        String cmd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        while(cmdObj.getFunction() != "quit") {
            try {
                System.out.print("Command > ");
                cmd = br.readLine();
                cmdObj = Magic8Parser.parseCommand(cmd);
                
                switch(cmdObj.getFunction()) {
                    case "add":
                        System.out.println("Calling out add method");
                        break;
                    case "clear":
                        System.out.println("Calling out clear method");
                        break;
                    case "delete":
                        System.out.println("Calling out delete method");
                        break;
                    case "display":
                        System.out.println("Calling out display method");
                        break;
                    case "edit":
                        System.out.println("Calling out edit method");
                        break;
                    case "help":
                        System.out.println("Calling out help method");
                        break;
                    case "search":
                        System.out.println("Calling out search method");
                        break;
                    case "undo":
                        System.out.println("Calling out undo method");
                        break;
                }
                
            } catch(IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }
}
