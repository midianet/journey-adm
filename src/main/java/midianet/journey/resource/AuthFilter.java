package midianet.journey.resource;

import midianet.journey.util.CookieUtil;
import midianet.journey.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
	
	@Value("${cookie}")
	private String cookie;

	@Autowired
	private AuthService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String token = CookieUtil.getValue(request, cookie);
		boolean valid;
		if(StringUtils.isEmpty(token)){
			valid = false;
		}else{
			valid = service.valid(token);
		}
		if(valid) {
			//request.setAttribute("username", username);
			chain.doFilter(request, response);
		}else {
			String authService = getFilterConfig().getInitParameter("auth.login");
			response.sendRedirect(authService + "?redirect=" + request.getRequestURL());
		}
	}
	
	
}
