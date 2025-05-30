package com.unisync.backend.features.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisync.backend.features.feed.model.Post;
import com.unisync.backend.features.messaging.model.Conversation;
import com.unisync.backend.features.networking.model.Connection;
import com.unisync.backend.features.notifications.model.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDateTime;
import java.util.List;
@Entity(name = "users")
@Indexed(index = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {
        "receivedNotifications", "actedNotifications", "posts",
        "conversationsAsAuthor", "conversationsAsRecipient",
        "initiatedConnections", "receivedConnections"
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    private String username;

    @Builder.Default
    private Boolean emailVerified = false;

    private String emailVerificationToken;

    private LocalDateTime emailVerificationTokenExpiryDate;

    @JsonIgnore
    private String password;

    private String passwordResetToken;

    private LocalDateTime passwordResetTokenExpiryDate;

    @FullTextField(analyzer = "standard")
    private String firstName;

    @FullTextField(analyzer = "standard")
    private String lastName;

    @FullTextField(analyzer = "standard")
    private String company;

    @FullTextField(analyzer = "standard")
    private String position;

    private String location;

    private String profilePicture;

    private String coverPicture ;

    @Builder.Default
    private Boolean profileComplete = false;

    private String about;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> receivedNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> actedNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversationsAsAuthor;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversationsAsRecipient;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> initiatedConnections;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> receivedConnections;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
