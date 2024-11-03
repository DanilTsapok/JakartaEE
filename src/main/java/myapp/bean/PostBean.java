package myapp.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import myapp.entity.Post;
import myapp.entity.User;
import myapp.service.PostsService;
import myapp.service.UserService;

import java.util.List;

@Named
@RequestScoped
public class PostBean {

    @Inject
    private PostsService postsService;

    @Inject
    private UserService userService;

    private List<Post> posts;

    private Post post = new Post();
    @Named
    @Inject
    private UserBean userBean;


    public List<Post> getAllPosts() {
        posts = postsService.getAllPosts();
        return posts;
    }

    public PostBean (){
        this.postsService = new PostsService();
        loadPosts();
    }

    public void loadPosts(){
        assert postsService != null;
        posts = postsService.getAllPosts();
    }

    public List<Post> getPostsAuthUser(User creator) {
        posts = postsService.getAllPostsAuthUser(creator);
        return posts;
    }
    public void createPost() {
        User creator = userBean.getCurrentUser();
        if (post !=null){
            postsService.createPost(post, creator);
            post = new Post();
        }

    }
    public void deletePost(Long id){
        postsService.deletePost(id);
        loadPosts();

    }
    public void setPost(Post post) {
        this.post = post;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Post getPost() {
        return post;
    }



}
