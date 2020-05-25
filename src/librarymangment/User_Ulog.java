/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author yahya
 */
public class User_Ulog {
    
    public static boolean isUserLogin = false;
    public static Stage stage;
    public static String login_user;
    public static Date date = new Date();
    public static Timestamp timestamp = new Timestamp(date.getTime());
    public static File fileU = new File("log.txt");
        public static void addToLog(String Text){
        System.out.println(Text);
        try {
        Writer output = new FileWriter(fileU,true);
        output.write(login_user + Text + timestamp + "\n"); 
       
        output.close();

                
        } catch (Exception ex) {
            Logger.getLogger(User_Ulog.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
        
        public static void myAlert(String Title,String Content,int type){
        if(type == 0){
        Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Title);
            alert.setContentText(Content);
            alert.showAndWait();
        }else if(type == 1){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Title);
            alert.setContentText(Content);
            alert.showAndWait();
        }
    }

  
    
    
}
