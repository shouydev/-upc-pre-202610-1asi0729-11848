package com.meditrack.platform.u20241e275.shared.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jspecify.annotations.Nullable;

/**
 * Represents an error resource for REST responses, including a code, message, and optional details.
 * @param code
 * @param message
 * @param details
 * @author Joel Huamani Estefanero
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResource(String code, String message, @Nullable String details) {

    /**
     * Creates an ErrorResource with code and message only.
     */
    public ErrorResource(String code, String message) {
        this(code, message, null);
    }
}
