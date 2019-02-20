package com.mark.common.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {
    private Long userId;
    private String password;
    private final String username;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public String toString() {
        return "JwtUserDetails{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialNonExpired=" + credentialNonExpired +
                ", authorities=" + authorities +
                '}';
    }

    public JwtUserDetails(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        this(userId, username, "", true, true, true, true, authorities);
    }

    public JwtUserDetails(Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialNonExpired, Collection<? extends GrantedAuthority> authorities) {
        if (username != null && !"".equals(username) && password != null) {
            this.userId = userId;
            this.password = password;
            this.username = username;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.credentialNonExpired = credentialNonExpired;
            this.authorities = authorities;
        } else {
            throw new IllegalArgumentException("cannot pass null or empty value to constructor");
        }
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isCredentialNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
