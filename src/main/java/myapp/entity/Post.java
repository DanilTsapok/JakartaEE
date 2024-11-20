package myapp.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "\"Post\"")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_id_gen")
    @SequenceGenerator(name = "User_id_gen", sequenceName = "User_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name = "likes")
    private Number likes;
    @Column(name = "created_data")
    private Date createdData;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setLikes(Number likes) {
        this.likes = likes;
    }
    public Number getLikes() {
        return likes;
    }
    public void setCreatedData(Date createdData) {
        this.createdData = createdData;
    }
    public Date getCreatedData() {
        return createdData;
    }
    public void setCreator(User creator) {
        this.creator = creator;
    }
    public User getCreator() {
        return creator;
    }

    public Post(User creator, String name, String description, Number likes, Date createdData) {
        this.name=name;
        this.description=description;
        this.likes=likes;
        this.creator=creator;
        this.createdData=createdData;

    }
    public Post() {}

}
