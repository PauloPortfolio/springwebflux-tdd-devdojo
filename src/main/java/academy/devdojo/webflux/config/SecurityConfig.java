package academy.devdojo.webflux.config;

import academy.devdojo.webflux.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity //PreAuthorize exige esta anotacao
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterchain(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET,"/animes/**")
                .hasRole("USER")
                .pathMatchers(HttpMethod.PUT,"/animes/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.POST,"/animes/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.DELETE,"/animes/**")
                .hasRole("ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    //*****************************************//
    //*******  DATABASE AUTHENTICATION  *******//
    //*****************************************//
    @Bean
    ReactiveAuthenticationManager authenticationManager(SecurityService securityService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(securityService);
    }

    //***************************************//
    //*******  MEMORY AUTHENTICATION  *******//
    //***************************************//
    //    @Bean
    //    public MapReactiveUserDetailsService userDetailsService() {
    //        PasswordEncoder passwordEncoder =
    //                PasswordEncoderFactories
    //                        .createDelegatingPasswordEncoder();
    //
    //        UserDetails user = User
    //                .withUsername("user")
    //                .password(passwordEncoder.encode("devdojo"))
    //                .roles("USER")
    //                .build();
    //
    //        UserDetails admin = User
    //                .withUsername("admin")
    //                .password(passwordEncoder.encode("devdojo"))
    //                .roles("USER","ADMIN")
    //                .build();
    //
    //        return new MapReactiveUserDetailsService(user,admin);
    //
    //    }

}
