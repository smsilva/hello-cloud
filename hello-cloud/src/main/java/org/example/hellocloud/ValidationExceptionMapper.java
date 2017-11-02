package org.example.hellocloud;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
	Response.ResponseBuilder builder = Response
		.status(Response.Status.BAD_REQUEST);
	
	exception.getConstraintViolations()
		.forEach(v -> {
		    builder.header("Reason", v.getMessage());
		});
	
	return builder.build();
    }

}
