package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// "User" is a reserved word in some SQL implementations
@Entity
public class DBUser extends AbstractEntity {
    private String username;
    private String email;
    private String password;
    private String role;

    @NotNull
    @Size(min = 1, max = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @NotNull
    @Size(min = 4, max = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(min = 1, max = 255)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    private boolean locked = false;

    public DBUser() {
        // An empty constructor is needed for all beans
    }


//    public DBUser(String email, String username, String password, String role) {
//        // Sonst wird hier schon eine NPE geworfen.
//        Objects.requireNonNull(email);
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//        Objects.requireNonNull(role);
//
//        this.email = email;
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }


//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        ArrayList<GrantedAuthority> list = new ArrayList<>();
//        list.add((GrantedAuthority) () -> role);
//        return list;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return isEnabled();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return isEnabled();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return isEnabled();
//    }

//    @Override
//    public boolean isEnabled() {
//        return true;
//    }


}
