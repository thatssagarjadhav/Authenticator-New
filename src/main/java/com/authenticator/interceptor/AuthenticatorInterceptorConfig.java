package com.authenticator.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AuthenticatorInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	AuthenticatorInterceptor authenticationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//		java.util.List<String> pathList = new ArrayList<>();
		//		pathList.add("/service/**");
		registry.addInterceptor(authenticationInterceptor);
		//		registry.addInterceptor(cimsCustomizedServiceInterceptor).addPathPatterns(pathList);
		//		pathList = new ArrayList<>();
		//		pathList.add("/service/fileDetailsAPIController/**");
		//		registry.addInterceptor(jwtTokenInterceptor).addPathPatterns(pathList);
	}
}
