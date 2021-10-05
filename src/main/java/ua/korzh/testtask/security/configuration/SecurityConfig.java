package ua.korzh.testtask.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ua.korzh.testtask.security.filter.RegistrationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RegistrationFilter registrationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(16);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.httpBasic();
        http.addFilterAfter(registrationFilter, BasicAuthenticationFilter.class);
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST,"/location").hasAuthority("WRITE")
                .mvcMatchers(HttpMethod.GET, "/address" ,"/address/*").hasAnyAuthority("READ", "WRITE")
                .mvcMatchers(HttpMethod.POST, "/register").permitAll()
                .anyRequest().authenticated();
    }
}
