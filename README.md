# ChicosFASJavaCommonExceptionHandlers

common-exception-handlers is a library provided to developers to standardize error responses across all Chico's FAS web services.  Out of the box, Spring provides [DefaultErrorAttributes](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/web/servlet/error/DefaultErrorAttributes.html) used with @ControllerAdvice and @ExceptionHandler to promote a unified exception handling throughout a whole application.  The result is a basic, fluent error response:

```json
	{
	    "timestamp": "2019-11-01T19:32:55.941+0000",
	    "status": 400,
	    "error": "Bad Request",
	    "message": "Required request body is missing",
	    "path": "/customers/occ/800000"
	}
```

Chico's FAS has recognized the need to extend and enhance the basic error message to include additional telemetry information for troubleshooting purposes:

```json

	{
		"timestamp": "2019-11-01T19:33:58.615+0000",
		"status": 400,
		"error": "10000",
		"message": "400 Bad Request",
		"path": "/customers/occ/800000",
    	"traceId": "de2a096dd1e30965",
		"request": {
			"profileId": "ABC",
			"profile": {
				"firstName": "Brian"
			},
			"siteId": "whbmotr"
		}
	}
```

**traceId** provides a GUID which can be found in log files and distributed tracing.  Tracing must be enabled for the web service.

**request** provides the @RequestBody sent to the web service for debugging purposes.  The request bean must be saved in the controller via [RequestContext](https://github.com/ChicosFAS-DRT/ChicosFASJavaCommonExceptionHandlers/blob/master/src/main/java/com/chicosfas/common/exception/RequestContext.java) to be available to the library:

```java
public class CustomerController {

	@Autowired
	protected RequestContext requestContext;

	@PostMapping("/{brand}")
	public ResponseEntity<Void> passwordReset(@PathVariable Integer brand, @RequestBody Email email) {
		requestContext.setRequestBody(email);

		resetService.resetPassword(brand, email);

		return new ResponseEntity<>(HttpStatus.OK);
	}	
}
```

## Usage

Add the following dependency to your project's pom.xml file.

```xml
	<dependency>
		<groupId>com.chicosfas</groupId>
		<artifactId>common-exception-handlers</artifactId>
		<version>1.0.0-RELEASE</version>
	</dependency>
```

The following exceptions are handled by the default implementation:

* ConflictException
* InternalServerException
* MethodArgumentTypeMismatchException
* SqlException
* StatusCodeException
* TimeoutException

## Extending for additional exceptions

For project-specific exception handling, create a subclass of DefaultExceptionController for new exceptions or to redefine the fields or format.

An example can be found in the customer-web-services project [here](https://github.com/ChicosFAS-DRT/ChicosFASJavaCustomerWebServices/blob/master/src/main/java/com/chicosfas/ExceptionController.java).  In this example, support has been added for:

* AccessDeniedException
* DataAccessException
* HystrixRuntimeException
