package librarymangment;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yahya
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Books.findAll",
            query = "Select b From Books b"),
    
    @NamedQuery(name = "Books.findBook",
            query = "Select b From Books b where b.id = :id"),
    
})
public class Books implements Serializable {
      @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;
      private String name;
      private String description;
      
      public Books(){}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
      
    
}
