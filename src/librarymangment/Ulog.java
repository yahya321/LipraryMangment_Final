/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 *
 * @author yahya
 */
public class Ulog {
    
    public static boolean isUserLogin = false;

    public static Stage stage;
    
    public static void addToLog(String Text){
        System.out.println(Text);
        FileOutputStream outputStream;
        try {
            File lo = new File("log.txt");
            FileWriter fileWriter = new FileWriter(lo);
            fileWriter.append(Text);

                
        } catch (Exception ex) {
            Logger.getLogger(Ulog.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }

  
    
    
}
