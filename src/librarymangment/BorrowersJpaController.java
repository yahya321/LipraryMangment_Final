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
public class BorrowersJpaController implements Serializable {

    public BorrowersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Borrowers borrowers) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(borrowers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Borrowers borrowers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            borrowers = em.merge(borrowers);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = borrowers.getId();
                if (findBorrowers(id) == null) {
                    throw new NonexistentEntityException("The borrowers with id " + id + " no longer exists.");
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
            Borrowers borrowers;
            try {
                borrowers = em.getReference(Borrowers.class, id);
                borrowers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The borrowers with id " + id + " no longer exists.", enfe);
            }
            em.remove(borrowers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Borrowers> findBorrowersEntities() {
        return findBorrowersEntities(true, -1, -1);
    }

    public List<Borrowers> findBorrowersEntities(int maxResults, int firstResult) {
        return findBorrowersEntities(false, maxResults, firstResult);
    }

    private List<Borrowers> findBorrowersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Borrowers.class));
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

    public Borrowers findBorrowers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Borrowers.class, id);
        } finally {
            em.close();
        }
    }

    public int getBorrowersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Borrowers> rt = cq.from(Borrowers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
