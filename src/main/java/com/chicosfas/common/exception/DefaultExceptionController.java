/*<CHICOSCOPYRIGHT>
*
* Copyright (C) 2006-2019 Chico's.
* All Rights Reserved.  No use, copying or distribution
* of this work may be made except in accordance with a
* valid license agreement from Chico's.  This notice
* must be included on all copies, modifications and
* derivatives of this work.
*
* Chico's MAKES NO REPRESENTATIONS OR WARRANTIES
* ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
* WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
* PURPOSE, OR NON-INFRINGEMENT. MY COMPANY SHALL NOT BE
* LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
* OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
* DERIVATIVES.
*
*</CHICOSCOPYRIGHT>*/

package com.chicosfas.common.exception;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class DefaultExceptionController {

	@Autowired
	protected Tracer tracer;

	@Autowired
	protected RequestContext requestContext;

	public static final String ERROR = "error";
	public static final String PATH = "path";
	public static final String MESSAGE = "message";
	public static final String REQUEST = "request";
	public static final String STATUS = "status";
	public static final String TIMESTAMP = "timestamp";
	public static final String TRACE_ID = "traceId";

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, Object> methodArgumentTypeMismatchException(Exception e, WebRequest webRequest) {
		log.error("MethodArgumentTypeMismatchException=", e);
		return buildException(webRequest, String.valueOf(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
	}

	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public Map<String, Object> unauthorizedAccess(Exception e, WebRequest webRequest) {
		log.error("AccessDeniedException=", e);
		return buildException(webRequest, String.valueOf(HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
	}

	@ExceptionHandler({ ConflictException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public Map<String, Object> conflictException(Exception e, WebRequest webRequest) {
		log.error("ConflictException=", e.getCause());
		return buildException(webRequest, String.valueOf(HttpStatus.CONFLICT.value()),
				HttpStatus.CONFLICT.getReasonPhrase(), "Request conflicts with the current state of the server");
	}

	@ExceptionHandler({ InternalServerException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> internalServerException(Exception e, WebRequest webRequest) {
		log.error("InternalServerException=", e.getCause());
		return buildException(webRequest, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getCause().getMessage());
	}

	@ExceptionHandler({ StatusCodeException.class })
	@ResponseBody
	Map<String, Object> statusCodeException(StatusCodeException e, WebRequest webRequest,
			HttpServletResponse response) {
		log.error("StatusCodeException={}", e.getMessage());
		response.setStatus(e.getStatus().value());
		return buildException(webRequest, String.valueOf(e.getStatus().value()), e.getCode(), e.getMessage());
	}

	@ExceptionHandler({ SQLException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> sqlException(Exception e, WebRequest webRequest) {
		log.error("SQLException=", e.getCause());
		return buildException(webRequest, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getCause().getMessage());
	}

	@ExceptionHandler({ TimeoutException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
	public Map<String, Object> timeoutException(Throwable t, WebRequest webRequest) {
		log.error("TimeoutException={}", t.getMessage());
		return buildException(webRequest, String.valueOf(HttpStatus.REQUEST_TIMEOUT.value()),
				HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
	}

	public Map<String, Object> buildException(WebRequest webRequest, String status, String error, String message) {
		Map<String, Object> exception = new HashMap<>();
		exception.put(STATUS, status);
		exception.put(ERROR, error);
		exception.put(MESSAGE, message);
		exception.put(TIMESTAMP, new Date());

		if (tracer != null && tracer.activeSpan() != null) {
			exception.put(TRACE_ID, tracer.activeSpan().context().toTraceId());
		}

		if (requestContext != null && requestContext.getRequestBody() != null) {
			exception.put(REQUEST, requestContext.getRequestBody());
		}

		String path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
		if (path != null) {
			exception.put(PATH, path);
		}

		return exception;
	}
}