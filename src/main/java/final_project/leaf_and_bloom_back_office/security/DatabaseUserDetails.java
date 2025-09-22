package final_project.leaf_and_bloom_back_office.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import final_project.leaf_and_bloom_back_office.model.User;

public class DatabaseUserDetails implements UserDetails {
    private final Integer id;
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    // constructor creates DatabaseUserDetails from a User entity - copying ID,
    // username and password from the database object and initializing an empty
    // HashSet for authorities (can add roles here if requested in security config -
    // wasn't necessary here)
    public DatabaseUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = new HashSet<GrantedAuthority>();

        // only one role - thus this line is unnecessary because in my security config,
        // i only check if authenticated
        // authorities.add(new SimpleGrantedAuthority("ADMIN"));
    }

    public Integer getId() {
        return this.id;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // all accounts are active, unlocked and usable - these are checked by Spring
    // Security before proceeding with authentication
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
