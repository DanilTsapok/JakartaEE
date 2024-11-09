import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import myapp.entity.Post;
import myapp.entity.User;
import myapp.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        UserService userService = new UserService();
        User user = new User("Danil", "danya@gmail.com", userService.hashPassword("12345"));
        Post post = new Post(user, "Test", "test", 1, new Date());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.persist(post);
        em.getTransaction().commit();
        em.close();

    }
}