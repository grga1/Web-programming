package mk.ukim.finki.wp.jan2025g2.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class is used to configure user login on path '/login' and logout on path '/logout'.
 * After login, the user should be redirected to the '/national-parks' page. After logout,
 * the user should be redirected to the '/' page.
 * The public pages in the application should be '/' and '/national-parks'.
 * All other pages should be visible only for a user with admin role.
 * Furthermore, in the "list.html" template, the 'Edit', 'Delete', 'Add' and 'Close' buttons should only be
 * visible for a user with admin role.
 * <p>
 * For login inMemory users should be used. Their credentials are given below:
 * [{
 * username: "user",
 * password: "user",
 * role: "USER"
 * },
 * <p>
 * {
 * username: "admin",
 * password: "admin",
 * role: "ADMIN"
 * }]
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;


    /**
     * Remove the implementation of the following method and replace it with your own code to implement the security requests.
     * If you do not wish to implement the security requests, leave this code as it is.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/national-parks")
                        .permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().hasRole("ADMIN")
//                        .authenticated()
                )
                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
                        .failureUrl("/login?error=BadCredentials")
                        .defaultSuccessUrl("/national-parks", true)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling((ex) -> ex
                        .accessDeniedPage("/access_denied")
                );

        return http.build();
    }

    // In Memory Authentication
        @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1,admin);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .headers(httpSecurityHeadersConfigurer ->
//                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
//                )
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/**")
//                        .permitAll()
//                );
//        return http.build();
//    }
}
