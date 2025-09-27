//package com.Gateway.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    // Password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // Main security filter chain
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session
//                )
//                .authorizeHttpRequests(authz -> authz
//                        // Public endpoints
//                        .requestMatchers(
//                                "/auth/**",
//                                "/createPassword",
//                                "/test/**"
//                        ).permitAll()
//                        // All other endpoints require authentication
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(basic -> basic.disable())  // Disable HTTP Basic auth
//                .formLogin(form -> form.disable());   // Disable form login
//
//        return http.build();
//    }
//
//    // CORS configuration for cross-service or frontend access
////    @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurer() {
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/**")  // Apply to all endpoints
////                        .allowedOrigins("*") // Allow requests from any origin
////                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
////                        .allowedHeaders("*")
////                        .allowCredentials(false);
////            }
////        };
////    }
//}
