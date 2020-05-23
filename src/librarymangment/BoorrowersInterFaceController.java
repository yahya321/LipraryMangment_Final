/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import librarymangment.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author yahya
 */
public class BoorrowersInterFaceController implements Initializable {
    ObservableList<String> genderStatus = FXCollections.observableArrayList("Male","Female");
    @FXML
    private TableView<Borrowers> tvBorrower;
    @FXML
    private TableColumn<Borrowers, Integer> tcID;
    @FXML
    private TableColumn<Borrowers, String> tcFName;
    @FXML
    private TableColumn<Borrowers, String> tcLName;
    @FXML
    private TableColumn<Borrowers, Integer> tcMobile;
    @FXML
    private TableColumn<Borrowers, String> tcEmail;
    @FXML
    private TableColumn<Borrowers, String> tcAddress;
    @FXML
    private TableColumn<Borrowers, Integer> tcGender;
    @FXML
    private TextField tfFName;
    @FXML
    private TextField tfLName;
    @FXML
    private TextField tfMobile;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private ChoiceBox cbGender;
    @FXML
    private Button buttonResetField;
    @FXML
    private Button buttonShowBorrowers;
    @FXML
    private Button buttonADDBorrowers;
    @FXML
    private Button buttonEditBorrowers;
    @FXML
    private Button buttonDelBorrowers;
    private int id;
    private EntityManagerFactory emf;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbGender.setItems(genderStatus);
        cbGender.setValue("Male");
        tcID.setCellValueFactory(new PropertyValueFactory("id"));
        tcFName.setCellValueFactory(new PropertyValueFactory("first_name"));
        tcLName.setCellValueFactory(new PropertyValueFactory("last_name"));
        tcMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        tcEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tcAddress.setCellValueFactory(new PropertyValueFactory("address"));
        tcGender.setCellValueFactory(new PropertyValueFactory("gender"));
        
        
        tvBorrower.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedBorrower() );
        this.emf = Persistence.createEntityManagerFactory("LibraryMangmentPU");
        
        
        }    

    @FXML
    private void buttonResetFieldHandle(ActionEvent event) {
        resetControls();
        
        
    }

    @FXML
    private void buttonShowBorrowersHandle(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Borrowers> borrower = em.createNamedQuery("Borrowers.findAll").getResultList();
        tvBorrower.getItems().setAll(borrower);
        em.close();
    }

    @FXML
    private void buttonADDBorrowersHandle(ActionEvent event) {
        Borrowers borrowers = new Borrowers();
        borrowers.setFirst_name(tfFName.getText());
        borrowers.setLast_name(tfLName.getText());
        borrowers.setMobile(Integer.parseInt(tfMobile.getText()));
        borrowers.setEmail(tfEmail.getText());
        borrowers.setAddress(tfAddress.getText());
        if(cbGender.getValue().equals("Male")){
            borrowers.setGender(0);}
        if(cbGender.getValue().equals("Female")){
            borrowers.setGender(1);}
        BorrowersJpaController bojpcon = new BorrowersJpaController(this.emf);
        bojpcon.create(borrowers);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setContentText("The item was Added");
        alert.showAndWait();
        RefreshTable();

        
    }

    @FXML
    private void buttonEditBorrowersHandle(ActionEvent event) throws Exception {
       Borrowers borrowers = new Borrowers();
       borrowers.setId(id);
       borrowers.setFirst_name(tfFName.getText());
       borrowers.setLast_name(tfLName.getText());
       borrowers.setMobile(Integer.parseInt(tfMobile.getText()));
       borrowers.setEmail(tfEmail.getText());
       borrowers.setAddress(tfAddress.getText());
       if(cbGender.getValue().equals("Male")){
            borrowers.setGender(0);}
       if(cbGender.getValue().equals("Female")){
            borrowers.setGender(1);}
       BorrowersJpaController bojpcon = new BorrowersJpaController(this.emf);
       bojpcon.edit(borrowers);
       RefreshTextFields();
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Done");
       alert.setContentText("The item was Edited");
       alert.showAndWait();
       RefreshTable();


    }

    @FXML
    private void buttonDelBorrowersHandle(ActionEvent event) throws NonexistentEntityException {
        BorrowersJpaController bojpcon = new BorrowersJpaController(this.emf); 
        bojpcon.destroy(id);
        RefreshTextFields();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setContentText("the Item was Deleted");
        alert.showAndWait();
        RefreshTable();
    }
     private void showSelectedBorrower(){
        Borrowers borrowers = tvBorrower.getSelectionModel().getSelectedItem();
        if(borrowers != null){
        id = borrowers.getId();
       // tfID.setText(String.valueOf(borrowers.getId()));
        tfFName.setText(borrowers.getFirst_name());
        tfLName.setText(borrowers.getLast_name());
        tfMobile.setText(String.valueOf(borrowers.getMobile()));
        tfEmail.setText(borrowers.getEmail());
        tfAddress.setText(borrowers.getAddress());
        if(borrowers.getGender()==0){
            cbGender.setValue("Male");
        }else if(borrowers.getGender()==1){
            cbGender.setValue("Female");
        }

        }
     }
     private void resetControls(){
        
        tfFName.setText("");
        tfLName.setText("");
        tfMobile.setText("");
        tfEmail.setText("");
        tfAddress.setText("");
        cbGender.setValue("Male");
        tvBorrower.getItems().clear();
    
    }
     private void RefreshTable(){
        EntityManager em = emf.createEntityManager();
        List<Borrowers> borrower = em.createNamedQuery("Borrowers.findAll").getResultList();
        tvBorrower.getItems().setAll(borrower);
        em.close();
        
    
    }
     private void RefreshTextFields(){
        
        tfFName.setText("");
        tfLName.setText("");
        tfMobile.setText("");
        tfEmail.setText("");
        tfAddress.setText("");
        cbGender.setValue("Male");
    
    }
     
    
}
