package org.santanu.santanubrains.rxjava.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.santanu.santanubrains.rxjava.response.ErrorResponse;

@Provider
public class StockSymbolNotFoundMapper implements ExceptionMapper<StockSymbolNotFoundException> {


	@Override
	public Response toResponse(StockSymbolNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(ExceptionConfig.NOT_FOUND, exception.getMessage(), ExceptionConfig.ERROR_URL);
		return Response.status(Status.NOT_FOUND).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
