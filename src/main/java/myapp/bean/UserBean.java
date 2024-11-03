package myapp.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import myapp.entity.User;
import myapp.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Named
@RequestScoped
public class UserBean {
    private User user = new User();
    private List<User> users;
    private List<String> userInfoList;
    private final UserService userService;

    public User getCurrentUser(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return (User) session.getAttribute("user");
    }

    public UserBean() {
        this.userService = new UserService();
        loadUsers();
    }

    public void loadUsers() {
        users = userService.findAllUsers(10);
    }

    public String registerUser(){
        try{
            userService.registerUser(user);
            loadUsers();
            user = new User();
            return "/index.xhtml?faces-redirect=true";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String loginUser(){
        try{
           User loginUser = userService.login(user.getEmail(),user.getPassword());
            if(loginUser != null){
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("user", loginUser);
                return "/Home.xhtml?faces-redirect=true";
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Email or Password"));
                return "/LoginForm.xhtml?faces-redirect=true";
            }
        }catch (NoSuchAlgorithmException e){
            return "/LoginForm.xhtml?faces-redirect=true";
        }
    }
    public String logout(){
        userService.Logout();
        loadUsers();
        return "/Home.xhtml?faces-redirect=true";
    }
    public String createUser() {
        userService.createUser(user);
        loadUsers();
        user = new User();
        return "users";
    }

    public String editUser(Long id) {
        user = userService.findById(id);
        return "editUser";
    }

    public String updateUser() {
        if (user.getId() != null) {
            userService.updateUser(user);
        }
        loadUsers();
        user = new User();
        return "users";
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
        loadUsers();
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getUserCount() {
        return users != null ? users.size() : 0;
    }


    public List<String> getUserInfoList() {
        return userInfoList;
    }


}
