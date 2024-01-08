package vasler.devicelab._config_;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .securityContext(security -> security
                .requireExplicitSave(false))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/").authenticated()
                .requestMatchers("/ui").authenticated()
                .requestMatchers("/ui/**").authenticated())
            .formLogin(formLogin -> formLogin.defaultSuccessUrl("/ui/testers", true));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
//        final List<UserDetails> users = new ArrayList<UserDetails>();
//        Stream.iterate(1, i -> i <= 10, i -> i + 1).forEach(i -> {
//            users.add(User.withUsername("user.%d".formatted(i))
//                    .password("password")
//                    .authorities("user")
//                    .build());
//        });
        return new InMemoryUserDetailsManager(User.withUsername("admin").password("password").build());
    }
}
