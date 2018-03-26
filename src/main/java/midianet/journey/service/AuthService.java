package midianet.journey.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

	@Value("${auth-check}")
	private String authCheck;
	
	//TODO HYSTRIX HERE
	public boolean valid(String token){
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getForObject(String.format("%s?token=%s",authCheck,token), String.class);
			return true;
		}catch (HttpStatusCodeException e){
			return false;
		}
		
	}
	
}