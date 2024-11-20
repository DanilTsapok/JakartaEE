package myapp.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import myapp.entity.Post;
import myapp.entity.User;
import myapp.service.PostsService;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
public class PostBean {

    @Inject
    private PostsService postsService;

    @Inject
    private UserBean userBean;

    private List<Post> posts;

    private List<Post> userPosts;
    private Post post = new Post();


    public PostBean() {
        this.postsService = new PostsService();
        loadPosts();
    }
    public void loadPosts() {
        posts = postsService.getAllPosts();
    }

    public String createPost() {
        User creator = userBean.getCurrentUser();
        postsService.createPost(post, creator);
        post = new Post();
        loadPosts();
        return "Home";
    }

    public void deletePost(Long id) {
        postsService.deletePost(id);
        loadPosts();
    }

    public String userProfile() {
        userPosts=postsService.getAllPostsAuthUser(userBean.getUser().getId());
        return "UserProfile";
    }

    public String getDifferenceBetweenDate(Date date) {
        LocalDateTime postDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime currentDate = LocalDateTime.now();
        Duration duration = Duration.between(postDate, currentDate);

        if (duration.toDays() > 0) {
            return duration.toDays() + " days ago";
        } else if (duration.toHours() > 0) {
            return duration.toHours() + " hours ago";
        } else if(duration.toMinutes() > 0) {
            return duration.toMinutes() + " minutes ago";
        }else{
            return duration.toSeconds() + " seconds ago";
        }
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Post getPost() {
        return post;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }
    public List<Post> setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
        return userPosts;
    }

}