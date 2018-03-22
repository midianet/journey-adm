package midianet.journey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/").authenticated()//.permitAll()
            .antMatchers("/api/**").permitAll()   //.authenticated()
            .and()
            .csrf().disable()
          .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
          .logout()
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("midianet").password("P@$w00rd").roles("ADMIN","USER").and()
                .withUser("admin").password("xpto").roles("ADMIN","USER")
        ;
    }
}
