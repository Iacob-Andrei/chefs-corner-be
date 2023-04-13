package com.chefscorner.apigateway.filter;

import com.chefscorner.apigateway.exception.InvalidTokenException;
import com.chefscorner.apigateway.exception.MissingAuthorizationHeaderException;
import com.chefscorner.apigateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(validator.isSecured.test(exchange.getRequest())){
                //header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new MissingAuthorizationHeaderException();
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }

                try{
                    jwtUtil.validateToken(authHeader);
                }catch (Exception e){
                    throw new InvalidTokenException();
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{
    }
}
