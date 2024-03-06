package com.ecomerce.my.ECommerce.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 3, max = 20, message = "First Name must be between 3 and 20 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "First Name must not contains numbers or special character")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 3, max = 20, message = "Last Name must be between 3 and 20 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Last Name must not contains numbers or special character")
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8, message = "password must be at lest 8 character long")
    @NotBlank
    private String password;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\d+$")
    @NotBlank
    private String phoneNumber;

    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany
    @JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> address = new ArrayList<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens = new HashSet<>();

    public User(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
//    convenience method
    public void addToken(Token token) {
        if (tokens == null) {
            tokens = new HashSet<>();
        }
        token.setUser(this);
        this.tokens.add(token);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
