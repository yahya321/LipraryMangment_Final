/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package librarymangment;

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
    @NamedQuery(name = "Users.findId",
            query = "Select u From Users u where u.name = :name")
})
public class Users {
      @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;
      private String name;
      private String password;

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setpassword(String password) {
        this.password = password;
    }
      
    
}
