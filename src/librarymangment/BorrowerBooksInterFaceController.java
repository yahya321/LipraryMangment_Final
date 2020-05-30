/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import librarymangment.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author yahya
 */
public class BorrowerBooksInterFaceController implements Initializable {

    @FXML
    private TableView<borrower_books> tvBorrowerBook;
    @FXML
    private TableColumn<borrower_books, Integer> tcID;
    @FXML
    private TableColumn<borrower_books, Integer> tcBookID;
    @FXML
    private TableColumn<borrower_books, Integer> tcBorrowerID;
    @FXML
    private TableColumn<borrower_books, Timestamp> tcBorrowingDate;
    @FXML
    private TableColumn<borrower_books, Timestamp> tcBookReturndeDate;
    @FXML
    private TextField tfBookID;
    @FXML
    private TextField tfBorrowerID;
    @FXML
    private Button buttonResetField;
    @FXML
    private Button buttonShow;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonADDBorrowerBook;
    @FXML
    private Button buttonReturnBorroBook;
    private EntityManagerFactory emf;
    private int id;
    private Timestamp ti;
    private int bookId;
    private int BorrowerId;
    
    Date date = new Date();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcID.setCellValueFactory(new PropertyValueFactory("id"));
        tcBookID.setCellValueFactory(new PropertyValueFactory("book_id"));
        tcBorrowerID.setCellValueFactory(new PropertyValueFactory("borrower_id"));
        tcBorrowingDate.setCellValueFactory(new PropertyValueFactory("borrowers_date"));
        tcBookReturndeDate.setCellValueFactory(new PropertyValueFactory("return_date"));
        tvBorrowerBook.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedBorrowerBook() );
        this.emf = Persistence.createEntityManagerFactory("LibraryMangmentPU");
    }    

    @FXML
    private void buttonResetFieldHandle(ActionEvent event) {
        resetControls();
    }

    @FXML
    private void buttonShowHandle(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<borrower_books> borrowerbooks = em.createNamedQuery("borrower_books.findAll").getResultList();
        System.out.println(borrowerbooks);
        tvBorrowerBook.getItems().setAll(borrowerbooks);
        em.close();
    
    }

    @FXML
    private void buttonDeleteHandle(ActionEvent event) throws NonexistentEntityException {
       EntityManager em = emf.createEntityManager();
       List<borrower_books> listBorrowerbooks  = em.createNamedQuery("borrower_books.findBorrowerBook").setParameter("id", this.id).getResultList(); 
       if(!listBorrowerbooks.isEmpty()){
       borrower_booksJpaController borrowerbooks = new borrower_booksJpaController(this.emf); 
       borrowerbooks.destroy(this.id); 
       RefreshTextFields();
       User_Ulog.myAlert("Operation Complete", "The item was Deleted",1);
       RefreshTable();
       User_Ulog.addToLog(" Deleted  Borrower book with ID { "+ this.id+" } From"+ " borrower_books " +"On ");
        }else{
          User_Ulog.myAlert("No Item Selected", "Please Select an item from the Table",0);
        }
    }

    @FXML
    private void buttonADDBorrowerBookHandle(ActionEvent event) {
        if(!tfBookID.getText().isEmpty() && !tfBorrowerID.getText().isEmpty()){
        EntityManager em = emf.createEntityManager();
        int bookid = Integer.parseInt(tfBookID.getText());
        int borrowerid = Integer.parseInt(tfBorrowerID.getText());
        List<borrower_books> borrowerbooks1 = em.createNamedQuery("borrower_books.findBookunReturned").setParameter("bookid", bookid).getResultList();

        if(borrowerbooks1.isEmpty()){
        try{
            
            Books books = (Books)em.createNamedQuery("Books.findBook").setParameter("id", bookid).getSingleResult();
            Borrowers Borrower = (Borrowers)em.createNamedQuery("Borrowers.findBorrower").setParameter("id", borrowerid).getSingleResult();
            borrower_booksJpaController borrowerbooks = new borrower_booksJpaController(this.emf); 
            borrower_books borrowerbooks2 = new  borrower_books(bookid,borrowerid,new Timestamp(date.getTime()));
            borrowerbooks.create(borrowerbooks2);
            
            RefreshTextFields();
            User_Ulog.myAlert("Operation Complete", "The Item was Added",1);
            RefreshTable();
            User_Ulog.addToLog(" Added Book name { "+books.getName()+" } To Borrower name { "+Borrower.getFirst_name()+"} in"+ " borrower_books " +"On ");
            
        }catch(javax.persistence.NoResultException e){
            User_Ulog.myAlert("Error", "Please Enter Valid Book ID and Borrower ID",0);
        }          
        }else {
            User_Ulog.myAlert("Error", "Please Enter Non-borrowed book Or Returned Book ID",0);
        
        }
        }else{
        User_Ulog.myAlert("Invalid values", "Please Enter Valid Values",0);
        }
         
      
    }

    @FXML
    private void buttonReturnBorroBookHandle(ActionEvent event) throws Exception {
        if(!tfBookID.getText().isEmpty() && !tfBorrowerID.getText().isEmpty()){
        int bookid = Integer.parseInt(tfBookID.getText());
        int borrowerid = Integer.parseInt(tfBorrowerID.getText());
        borrower_booksJpaController borrowerbooks = new borrower_booksJpaController(this.emf); 
        borrower_books borrowerbooks1 = new  borrower_books();
        borrowerbooks1.setId(this.id);
        borrowerbooks1.setBook_id(bookid);
        borrowerbooks1.setBorrower_id(borrowerid);
        borrowerbooks1.setBorrowers_date(this.ti);
        borrowerbooks1.setReturn_date(new Timestamp(date.getTime()));
        borrowerbooks.edit(borrowerbooks1);
        RefreshTextFields();
        User_Ulog.myAlert("Operation Complete", "The Book was Returned to the liprary",1);
        RefreshTable();
        User_Ulog.addToLog(" Has Returned book with ID { "+ this.id+" }  in"+ " borrower_books " +"On ");
        }else{
        User_Ulog.myAlert("Invalid values", "Please Enter Valid Values",0);
        }
              
    }
    
     private void showSelectedBorrowerBook(){
        borrower_books borrowerbooks = tvBorrowerBook.getSelectionModel().getSelectedItem();
        if(borrowerbooks != null){
        this.id = borrowerbooks.getId();
        tfBookID.setText(String.valueOf(borrowerbooks.getBook_id()));
        tfBorrowerID.setText(String.valueOf(borrowerbooks.getBorrower_id()));
        this.ti = borrowerbooks.getBorrowers_date();
        }
     }
     private void resetControls(){
        
        tfBookID.setText("");
        tfBorrowerID.setText("");
        tvBorrowerBook.getItems().clear();
    
    }
    
     private void RefreshTable(){
        EntityManager em = emf.createEntityManager();
        List<borrower_books> borrowerbooks = em.createNamedQuery("borrower_books.findAll").getResultList();
        tvBorrowerBook.getItems().setAll(borrowerbooks);
        em.close();
        
    
    }
     private void RefreshTextFields(){
        
        tfBookID.setText("");
        tfBorrowerID.setText("");
        
    
    }
    
    
}
