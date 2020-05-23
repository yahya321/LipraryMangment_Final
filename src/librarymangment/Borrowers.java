package librarymangment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/**
 *
 * @author yahya
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Borrowers.findAll",
            query = "Select b From Borrowers b"),
    @NamedQuery(name = "Borrowers.findBorrower",
            query = "Select b From Borrowers b where b.id = :id"),
    
})
    

public class Borrowers implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)	 	 	
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer mobile;
    private String email;    
    private String address;    
    private Integer gender;

    public Borrowers() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getGender() {
        return gender;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
}
