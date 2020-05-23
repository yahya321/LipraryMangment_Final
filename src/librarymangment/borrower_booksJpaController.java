/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymangment;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import librarymangment.exceptions.NonexistentEntityException;

/**
 *
 * @author yahya
 */
public class borrower_booksJpaController implements Serializable {

    public borrower_booksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(borrower_books borrower_books) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(borrower_books);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(borrower_books borrower_books) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            borrower_books = em.merge(borrower_books);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = borrower_books.getBook_id();
                if (findborrower_books(id) == null) {
                    throw new NonexistentEntityException("The borrower_books with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            borrower_books borrower_books;
            try {
                borrower_books = em.getReference(borrower_books.class, id);
                borrower_books.getBook_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The borrower_books with id " + id + " no longer exists.", enfe);
            }
            em.remove(borrower_books);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<borrower_books> findborrower_booksEntities() {
        return findborrower_booksEntities(true, -1, -1);
    }

    public List<borrower_books> findborrower_booksEntities(int maxResults, int firstResult) {
        return findborrower_booksEntities(false, maxResults, firstResult);
    }

    private List<borrower_books> findborrower_booksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(borrower_books.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public borrower_books findborrower_books(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(borrower_books.class, id);
        } finally {
            em.close();
        }
    }

    public int getborrower_booksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<borrower_books> rt = cq.from(borrower_books.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
