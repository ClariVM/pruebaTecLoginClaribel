package com.login.claribel.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;

@Configuration
@EnableWebSecurity
public class WebAuthorization{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .requestMatchers("/api/**").permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login")
                .and()
                .logout()
                .logoutUrl("/logout") // URL para la solicitud de cierre de sesión
                .deleteCookies("JSESSIONID") // Eliminar la cookie de sesión
                .logoutSuccessUrl("/") // Redirigir después del cierre de sesión
                .permitAll() // Accesible para todos
                .and()
                .csrf().disable(); // Deshabilitar CSRF por simplicidad (considerar habilitar en producción)
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }

}
