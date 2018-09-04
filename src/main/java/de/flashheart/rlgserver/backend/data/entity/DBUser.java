package de.flashheart.rlgserver.backend.data.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

// "User" is a reserved word in some SQL implementations
@Entity
public class DBUser extends AbstractEntity implements UserDetails {

    @NotNull
    @Size(min = 1, max = 255)
    private String email;

    @NotNull
    @Size(min = 4, max = 255)
    private String password;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(min = 1, max = 255)
    private String role;

//    private boolean locked = false;

    public DBUser() {
        // An empty constructor is needed for all beans
    }

    public DBUser(String email, String username, String password, String role) {
        // Sonst wird hier schon eine NPE geworfen.
        Objects.requireNonNull(email);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(role);

        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add((GrantedAuthority) () -> role);
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
