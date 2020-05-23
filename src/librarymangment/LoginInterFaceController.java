/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author yahya
 */
public class LoginInterFaceController implements Initializable {

    @FXML
    private TextField tfUserName;
    private LibraryApp controller;

    @FXML
    private Button buttonLogin;
    private EntityManagerFactory emf;
    private Users users;
    @FXML
    private PasswordField tffPassword;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     this.emf = Persistence.createEntityManagerFactory("LibraryMangmentPU");
    }    

public void init (LibraryApp control){ 
    this.controller = control;
}
    @FXML
    private void buttonLoginHandle(ActionEvent event) throws IOException, Exception {
        EntityManager em = this.emf.createEntityManager();
        try{
        users = (Users) em.createNamedQuery("Users.findId")
                .setParameter("name",tfUserName.getText())
                .getSingleResult();
        if ( users.getPassword().equals(tffPassword.getText() ) ){
           
        Ulog.addToLog("User \""+users.getName()+"\" has been login");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Mangments.fxml")));
        Ulog.stage.setTitle("Mangments Options");
        Ulog.stage.setScene(scene);
        
        }else{
          JOptionPane.showMessageDialog(null, "rong pass");
         }
        
        }catch(NoResultException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Retrieving");
            alert.setContentText("No records found");
            alert.showAndWait();
        }
         
        em.close();
    }
    
    }
    

