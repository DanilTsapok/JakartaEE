package myapp.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import myapp.entity.Post;
import myapp.entity.User;

import java.util.Date;
import java.util.List;

@Stateless
public class PostsService {

    private EntityManagerFactory emf= Persistence.createEntityManagerFactory("default");

    public List<Post> getAllPosts(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Post> posts = em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
        em.getTransaction().commit();
        return posts;
    }

    public  List<Post> getAllPostsAuthUser(User creator){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p where p.creator= :creator", Post.class);
        query.setParameter("creator", creator);
        List<Post> posts = query.getResultList();
        em.getTransaction().commit();
        return posts;
    }

    public  void createPost(Post post, User creator ){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        post.setCreator(creator);
        post.setCreatedData(new Date());
        em.persist(post);
        em.getTransaction().commit();
        em.close();
    }
    public void deletePost(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Post post = em.find(Post.class, id);
        em.remove(post);
        em.getTransaction().commit();
        em.close();
    }

}
