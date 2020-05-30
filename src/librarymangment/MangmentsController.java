/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yahya
 */
public class MangmentsController implements Initializable {

    @FXML
    private Button buttonBooksMan;
    @FXML
    private Button buttonBorrowersMan;
    @FXML
    private Button buttonBorrBookMan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonBooksManHandle(ActionEvent event) throws Exception {
    Stage stage = new Stage(); 
    BOOKSMANGMENT_STAGE(stage);    
    }

    @FXML
    private void buttonBorrowersManHandle(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        BORROWERSMANGMENT_STAGE(stage);
    }

    @FXML
    private void buttonBorrBookManHandle(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        BorrowerBooks_STAGE(stage);
    }
    
    public void BOOKSMANGMENT_STAGE(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("BooksInterFace.fxml"));
        
        stage.setTitle("Books Mangment");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void BORROWERSMANGMENT_STAGE(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("BorrowersInterFace.fxml"));
        
        stage.setTitle("Borrowers Mangment");
        stage.setScene(new Scene(root));
        stage.show();
    }
     public void BorrowerBooks_STAGE(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("BorrowerBooksInterFace.fxml"));
        
        stage.setTitle("Borrowers Books Mangment");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
