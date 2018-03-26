package midianet.journey;

import midianet.journey.resource.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Application {
    
    @Value("${auth.login}")
    private String login;
    
    @Autowired
    private AuthFilter filter;
    
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setInitParameters(Collections.singletonMap("auth.login", login));
        registrationBean.addUrlPatterns("/app/*");
        return registrationBean;
    }
    
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}
