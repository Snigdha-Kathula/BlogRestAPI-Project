package com.springboot.blog.config;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder( ) {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        http.csrf(csrf->csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2ResourceServer(resourceServer-> resourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }
//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
//        return (context) -> {
//            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
//                context.getClaims().claims((claims) -> {
//                    Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
//                            .stream()
//                            .map(c -> c.replaceFirst("^ROLE_", ""))
//                            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
//                    claims.put("roles", roles);
//                });
//            }
//        };
//    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails siddu = User.builder()
//                .username("siddu")
//                .password(passwordEncoder().encode("siddu"))
//                .roles("USER")
//                .build();
//        UserDetails admin =User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(siddu, admin);
//    }
}
