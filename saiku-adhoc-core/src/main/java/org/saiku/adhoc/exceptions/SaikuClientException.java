package org.saiku.adhoc.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SaikuClientException extends WebApplicationException {
    public SaikuClientException(String message) {
        super(Response.status(400)
            .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}