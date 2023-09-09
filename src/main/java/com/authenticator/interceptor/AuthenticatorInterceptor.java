package com.authenticator.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.authenticator.authenticate.dto.response.ServiceResDTO;
import com.authenticator.authenticate.util.Validations;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticatorInterceptor implements HandlerInterceptor {

	//	@Value("${appId.allowed}")
	private String appId = "APP-a8bf3264f58259e263342e91522c7a07647014f68da3ad7466a7500110a7d91b";

	private static List<String> allowedAppIdList;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object handler) throws Exception {
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if (headerName.equalsIgnoreCase("access-control-request-method")) {
				return true;
			}
		}

		return performHeaderValidation(request, arg1, handler);
	}

	private boolean performHeaderValidation(HttpServletRequest request, HttpServletResponse httpServletRes, Object handler) throws Exception {
		if (Validations.isEmpty(request.getHeader("AppId"))) {
			ServiceResDTO response = new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode("API_AUTH_ERR_001").setStatusMessage("AppId is mandatory to be provided").build();
			httpServletRes.getWriter().write(new ObjectMapper().writeValueAsString(response));
			httpServletRes.setStatus(403);
			return false;
		}

		if (CollectionUtils.isEmpty(allowedAppIdList)) {
			String[] appIdArr = appId.split("~");
			allowedAppIdList = Arrays.asList(appIdArr);
		}

		if (allowedAppIdList.contains(Validations.trimInput(request.getHeader("AppId")))) {
			return true;
		} else {
			ServiceResDTO response = new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode("API_AUTH_ERR_002").setStatusMessage("Invalid AppId provided").build();
			httpServletRes.getWriter().write(new ObjectMapper().writeValueAsString(response));
			httpServletRes.setStatus(403);
			return false;
		}
	}

}
