package com.example.gateway.filters;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.gateway.beans.Users;

@Component
public class RoleBasedAuth extends AbstractGatewayFilterFactory<RoleBasedAuth.Config> {
	final Logger logger = LoggerFactory.getLogger(RoleBasedAuth.class);

	public RoleBasedAuth() {
		super(Config.class);
	}

	public static class Config {
		private List<String> allowedRoles;

		public List<String> getAllowedRoles() {
			return allowedRoles;
		}

		public void setAllowedRoles(List<String> allowedRoles) {
			this.allowedRoles = allowedRoles;
		}
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			// Pre-processing
			String authorization = exchange.getRequest().getHeaders().getFirst("Authorization").split(" ")[1];
			String decodedAuth = new String(Base64.getDecoder().decode(authorization));
			String username = decodedAuth.split(":")[0];
			String password = decodedAuth.split(":")[1];
			WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/user-services").build();
			Users user = null;
			try {
				user = webClient.get().uri("/user/{username}", username).retrieve().bodyToMono(Users.class).toFuture()
						.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			if (user != null && user.getPassword().equals(password)
					&& config.getAllowedRoles().contains(user.getDepartment())) {
				logger.info("Login successful, user is authorised to access " + exchange.getRequest().getURI());
				exchange.getResponse().setComplete();
			} else {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				exchange.getResponse().setComplete();
			}

			return chain.filter(exchange);
		};
	}
}
