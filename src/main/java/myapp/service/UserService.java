package myapp.service;

import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import myapp.entity.User;
import org.jboss.weld.context.http.Http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Stateless
public class UserService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public  void registerUser(User user) throws NoSuchAlgorithmException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        user.setPassword(hashPassword(user.getPassword()));
        String jpql = "SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", user.getEmail());
        if(!query.getResultList().isEmpty()) {
          em.close();
        }
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

     public User login(String email, String password) throws NoSuchAlgorithmException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String jpql = "SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        try{
            User user = query.getSingleResult();
            if(user.getPassword().equals(hashPassword(password))){
                return user;
            }
        }
        catch(NoResultException e){
            em.close();
        }
        return null;
     }
     public void Logout(){
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
         session.invalidate();

     }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hashedPassword){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return  sb.toString();
    }

    public void createUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public User readUser(Long id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }

    public void updateUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        if (em.getFlushMode() != FlushModeType.COMMIT) {
            em.setFlushMode(FlushModeType.COMMIT);
        }

        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }


    public void deleteUser(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<User> findAllUsers(int maxResults) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
        query.setMaxResults(maxResults);
        List<User> users = query.getResultList();
        em.close();
        return users;
    }

    public User findById(Long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createNamedQuery("User.findById", User.class);
        Parameter<Long> idParam = query.getParameter("id", Long.class);
        if (!query.isBound(idParam)) {
            query.setParameter(idParam, id);
        }
        User user = query.getSingleResult();
        em.close();
        return user;
    }


}
