package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class Magic8UITerminal {
    
    private static String cmd, filename;
    private static Magic8TaskList taskListManager = null;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    private static void invoke() throws IOException {
        new Magic8Controller(cmd, taskListManager);
    }
    
    private static void magic8UIInit() throws IOException, ParseException {        
        System.out.print("Filename: ");
        filename = br.readLine();        
        taskListManager = new Magic8TaskList(filename);
    }
    
    private static void magic8UIRun(){
        while(true) {
            try {
                System.out.print("Command > ");
                cmd = br.readLine();
                invoke();
            } catch (IOException e) {
                System.out.println(e.toString());
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }
 
    public static void main(String[] args) {   
        try {
            magic8UIInit();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(0);
        } catch (ParseException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        
        magic8UIRun();
    }
}
