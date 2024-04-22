package com.ps.culinarycompanion.user;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Entity(name = "user_details")
public class User implements UserDetails {

    @GeneratedValue
    @Id
    @Column(name = "user_id")
    private Integer userId;

    private String fullName;

    @Column(unique = true)
    @Nonnull
    private String email;

    @CurrentTimestamp
    private LocalDate joinDate;

    public User(String fullName, @Nonnull String email, LocalDate joinDate) {
        this.fullName = fullName;
        this.email = email;
        this.joinDate = joinDate;
    }

    public User() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Nonnull
    public String getFullName() {
        return fullName;
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

    public void setFullName(@Nonnull String username) {
        this.fullName = username;
    }

    @Nonnull
    @Override
    public String getUsername() {
        return email;
    }

    public void setEmail(@Nonnull String email) {
        this.email = email;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }
}
