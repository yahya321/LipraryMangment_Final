package librarymangment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author yahya
 */
public  class LibraryApp extends Application {
     Map<String, Pane> mapPanes = new TreeMap<>();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        User_Ulog.stage=primaryStage;
        Pane userPane = FXMLLoader.load(getClass().getResource("LoginInterFace.fxml"));
        Pane mangmentsPane = FXMLLoader.load(getClass().getResource("Mangments.fxml"));
        
        mapPanes.put("UserPane", userPane);
        mapPanes.put("MangmentsPane",mangmentsPane);
        
      
        if(User_Ulog.isUserLogin){
            
        Scene scene = new Scene(mapPanes.get("MangmentsPane"));
        User_Ulog.stage.setTitle("User MangmentsPane");
        User_Ulog.stage.setScene(scene);
        User_Ulog.stage.show();
        
        
           
        
        }else{
       Scene scene = new Scene(mapPanes.get("UserPane"));
            User_Ulog.stage.setTitle("User Login");
            User_Ulog.stage.setScene(scene);
            User_Ulog.stage.show();
        }
        
     
    }
    public static void main(String[] args) {
        launch(args);
    }
    
   
}
    
    

