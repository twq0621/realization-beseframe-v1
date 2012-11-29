package com.realization.wishwall.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理
 * 
 * 	请求方法抛出异常之后会进入此处理
 */
public class ForumHandlerExceptionResolver extends
		SimpleMappingExceptionResolver {
	protected ModelAndView doResolveException(
			javax.servlet.http.HttpServletRequest httpServletRequest,
			javax.servlet.http.HttpServletResponse httpServletResponse,
			java.lang.Object o, java.lang.Exception e) {
		httpServletRequest.setAttribute("ex", e);
		e.printStackTrace();
		return super.doResolveException(httpServletRequest,
				httpServletResponse, o, e);
	}
}
