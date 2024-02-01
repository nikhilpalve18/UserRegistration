package com.app.UserRegistration.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USER_DETAILS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String username;


    @Size(min = 8, message = "Password must be at least of 8 characters")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit"),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter"),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase letter"),
            @Pattern(regexp = "(?=.*[!@#$%^&*(),.?\":{}|<>]).+", message = "Password must contain at least one special character")
    })
    private String password;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = Contact.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    @Valid
    private List<Contact> contacts;

}
