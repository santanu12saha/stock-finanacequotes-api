package org.santanu.santanubrains.rxjava.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.santanu.santanubrains.rxjava.response.ErrorResponse;

@Provider
public class InternalErrorExceptionMapper implements ExceptionMapper<InternalErrorException> {

	@Override
	public Response toResponse(InternalErrorException exception) {
		ErrorResponse errorResponse = new ErrorResponse(ExceptionConfig.GENERIC_INTERNAL_SERVER_ERROR,
				exception.getMessage(), ExceptionConfig.ERROR_URL);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON)
				.build();
	}

}
