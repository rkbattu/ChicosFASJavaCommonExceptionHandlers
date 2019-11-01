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

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class DefaultExceptionControllerTest {

	@Test
	public void testConflictException() {
		// Class under test.
		DefaultExceptionController controller = new DefaultExceptionController();

		// Setup test.
		controller.tracer = new MockTracer();

		ConflictException e = new ConflictException();

		// Execute test.
		Map<String, Object> result = controller.conflictException(e,
				new ServletWebRequest(new MockHttpServletRequest()));

		// Assertions.
		assertEquals(409, result.get(DefaultExceptionController.STATUS));
		assertEquals("Conflict", result.get(DefaultExceptionController.ERROR));
		assertEquals("Request conflicts with the current state of the server",
				result.get(DefaultExceptionController.MESSAGE));
		assertEquals("FOOBAR", result.get(DefaultExceptionController.TRACE_ID));
	}

	@Test
	public void testInternalServerException() {
		// Class under test.
		DefaultExceptionController controller = new DefaultExceptionController();

		// Setup test.
		controller.tracer = new MockTracer();

		IllegalStateException e = new IllegalStateException();
		e.initCause(new Exception("FAILURE"));

		// Execute test.
		Map<String, Object> result = controller.internalServerException(e,
				new ServletWebRequest(new MockHttpServletRequest()));

		// Assertions.
		assertEquals(500, result.get(DefaultExceptionController.STATUS));
		assertEquals("Internal Server Error", result.get(DefaultExceptionController.ERROR));
		assertEquals("FAILURE", result.get(DefaultExceptionController.MESSAGE));
		assertEquals("FOOBAR", result.get(DefaultExceptionController.TRACE_ID));
	}

	@Test
	public void testMethodArgumentTypeMismatchException() {
		// Class under test.
		DefaultExceptionController controller = new DefaultExceptionController();

		// Setup test.
		controller.tracer = new MockTracer();

		MethodArgumentTypeMismatchException e = new MethodArgumentTypeMismatchException(null, null, null, null, null);

		// Execute test.
		Map<String, Object> result = controller.methodArgumentTypeMismatchException(e,
				new ServletWebRequest(new MockHttpServletRequest()));

		// Assertions.
		assertEquals(400, result.get(DefaultExceptionController.STATUS));
		assertEquals("Bad Request", result.get(DefaultExceptionController.ERROR));
		assertEquals("Failed to convert value of type 'null'", result.get(DefaultExceptionController.MESSAGE));
		assertEquals("FOOBAR", result.get(DefaultExceptionController.TRACE_ID));
	}

	@Test
	public void testStatusCodeException() {
		// Class under test.
		DefaultExceptionController controller = new DefaultExceptionController();

		// Setup test.
		controller.tracer = new MockTracer();

		StatusCodeException e = new StatusCodeException(401, "FOO", "BAR");

		// Execute test.
		Map<String, Object> result = controller.statusCodeException(e,
				new ServletWebRequest(new MockHttpServletRequest()), new MockHttpServletResponse());

		// Assertions.
		assertEquals(401, result.get(DefaultExceptionController.STATUS));
		assertEquals("FOO", result.get(DefaultExceptionController.ERROR));
		assertEquals("BAR", result.get(DefaultExceptionController.MESSAGE));
		assertEquals("FOOBAR", result.get(DefaultExceptionController.TRACE_ID));
	}

	@Test
	public void testSqlException() {
		// Class under test.
		DefaultExceptionController controller = new DefaultExceptionController();

		// Setup test.
		controller.tracer = new MockTracer();

		SQLException e = new SQLException();
		e.initCause(new Exception("FAILURE"));

		// Execute test.
		Map<String, Object> result = controller.sqlException(e, new ServletWebRequest(new MockHttpServletRequest()));

		// Assertions.
		assertEquals(500, result.get(DefaultExceptionController.STATUS));
		assertEquals("Internal Server Error", result.get(DefaultExceptionController.ERROR));
		assertEquals("FAILURE", result.get(DefaultExceptionController.MESSAGE));
		assertEquals("FOOBAR", result.get(DefaultExceptionController.TRACE_ID));
	}

	@Test
	public void testTimeoutException() {
		// Class under test.
		DefaultExceptionController controller = new DefaultExceptionController();

		// Setup test.
		controller.tracer = new MockTracer();

		TimeoutException e = new TimeoutException();

		// Execute test.
		Map<String, Object> result = controller.timeoutException(e,
				new ServletWebRequest(new MockHttpServletRequest()));

		// Assertions.
		assertEquals(408, result.get(DefaultExceptionController.STATUS));
		assertEquals("Request Timeout", result.get(DefaultExceptionController.ERROR));
		assertEquals("Request Timeout", result.get(DefaultExceptionController.MESSAGE));
		assertEquals("FOOBAR", result.get(DefaultExceptionController.TRACE_ID));
	}

}
