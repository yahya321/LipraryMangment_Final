/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import static librarymangment.User_Ulog.login_user;
import static librarymangment.User_Ulog.timestamp;

/**
 * FXML Controller class
 *
 * @author yahya
 */
public class BooksInterFaceController implements Initializable {
    @FXML
    private TableView<Books> tvBooks;
    @FXML
    private TableColumn<Books, Integer> tcID;
    @FXML
    private TableColumn<Books, String> tcName;
    @FXML
    private TableColumn<Books, String> tcDescription;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button buttonResetField;
    @FXML
    private Button buttonShowBook;
    @FXML
    private Button buttonADDBook;
    @FXML
    private Button buttonEditBook;
    @FXML
    private Button buttonDelBook;
    private EntityManagerFactory emf;
    private int id;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcID.setCellValueFactory(new PropertyValueFactory("id"));
        tcName.setCellValueFactory(new PropertyValueFactory("name"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("description"));
        tvBooks.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedBook() );
        this.emf = Persistence.createEntityManagerFactory("LibraryMangmentPU");


    }    

    @FXML
    private void buttonResetFieldHandle(ActionEvent event) {
        resetControls();
    }

    @FXML
    private void buttonShowBookHandle(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Books> bookss = em.createNamedQuery("Books.findAll").getResultList();
        tvBooks.getItems().setAll(bookss);
        em.close();
    }

    @FXML
    private void buttonADDBookHandle(ActionEvent event) throws FileNotFoundException {
        if(!tfName.getText().isEmpty() && !tfDescription.getText().isEmpty()){
        Books books = new Books();
        books.setName(tfName.getText());
        books.setDescription(tfDescription.getText());
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(books);
        em.getTransaction().commit();
        em.close();
        RefreshTextFields();
        User_Ulog.myAlert("Operation Complete", "The Item was Added",1);
        RefreshTable();
        User_Ulog.addToLog(" Added Book name { "+books.getName()+" } in"+ " books " +"On ");
        }else{
        User_Ulog.myAlert("Invalid values", "Please Enter Valid Values",0);
        }
        
    }

    @FXML
    private void buttonEditBookHandle(ActionEvent event) throws Exception {
       if(!tfName.getText().isEmpty() && !tfDescription.getText().isEmpty()){
        Books books = new Books();
        books.setId(this.id);
        books.setName(tfName.getText());
        books.setDescription(tfDescription.getText());
        BooksJpaController bookcontrol = new BooksJpaController(this.emf); 
        bookcontrol.edit(books);  
        RefreshTextFields();
        User_Ulog.myAlert("Operation Complete", "The item was Edited",1);
        RefreshTable();
        User_Ulog.addToLog(" Edited book with ID { "+this.id +" } in"+ " books " +"On ");
      }else{
        User_Ulog.myAlert("Invalid values", "Please Enter Valid Values",0);
       } 
    }

    @FXML
    private void buttonDelBookHandle(ActionEvent event) throws Exception {
         EntityManager em = emf.createEntityManager();
         List<Books> bookss = em.createNamedQuery("Books.findBook").setParameter("id", this.id).getResultList();
      if(!bookss.isEmpty()){
        BooksJpaController bookcontrol = new BooksJpaController(this.emf); 
        bookcontrol.destroy(this.id);
        RefreshTextFields();
        User_Ulog.myAlert("Operation Complete", "TThe item was Deleted",1);
        RefreshTable();
        User_Ulog.addToLog(" Deleted  book with ID { "+ this.id+" } From"+ " books " +"On ");
      }else{
          User_Ulog.myAlert("No Item Selected", "Please Select an item from the Table",0);
        }  

    }
    
    private void resetControls(){
        tfName.setText("");
        tfDescription.setText("");
        tvBooks.getItems().clear();
    
    }
    private void showSelectedBook(){
        Books books = tvBooks.getSelectionModel().getSelectedItem();
        if(books != null){
        this.id = books.getId();
        tfName.setText(books.getName());
        tfDescription.setText(books.getDescription());

        }
    }
    private void RefreshTable(){
        EntityManager em = emf.createEntityManager();
        List<Books> book = em.createNamedQuery("Books.findAll").getResultList();
        tvBooks.getItems().setAll(book);
        em.close();
        
    
    }
     private void RefreshTextFields(){
        
        tfName.setText("");
        tfDescription.setText("");
        
    }
    
}
