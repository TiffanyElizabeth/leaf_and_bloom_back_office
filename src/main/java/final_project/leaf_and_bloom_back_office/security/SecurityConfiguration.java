package final_project.leaf_and_bloom_back_office.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // tells Spring this class contains beans - methods that produce objects that
               // Spring manages
@EnableWebSecurity // enables Spring Security
public class SecurityConfiguration {

    @Bean // declares bean which configures how HTTP security works
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth // which URLS are public and which require login
                        .requestMatchers("/", "/login", "/api/**", "/webjars/**", "/css/**",
                                "/js/**")
                        .permitAll()
                        .requestMatchers("/teas/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form // configures login form behavior, forces redirect to /teas no matter where user
                                        // came from
                        .defaultSuccessUrl("/teas", true))

                .logout(Customizer.withDefaults()); // default logout page

        return http.build(); // builds and returns the securityfilterchain to Spring
    }

    @Bean // connects Spring Security to database for authentication using
          // DatabaseUserDetailsService to load users and PasswrodEncoder to check
          // passwords
    @SuppressWarnings("deprecation")
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
