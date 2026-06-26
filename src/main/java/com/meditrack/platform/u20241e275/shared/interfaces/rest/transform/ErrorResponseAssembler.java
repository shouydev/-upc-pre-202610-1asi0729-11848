package com.meditrack.platform.u20241e275.shared.interfaces.rest.transform;

import com.meditrack.platform.u20241e275.shared.application.result.ApplicationError;
import com.meditrack.platform.u20241e275.shared.interfaces.rest.resources.ErrorResource;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Assembles error responses for REST APIs based on application errors. It maps application error codes to HTTP status codes and localized messages.
 * The class provides methods to convert an ApplicationError into a ResponseEntity containing an ErrorResource, which includes the error code, localized message, and any additional details. It also handles localization of error messages based on the current locale.
 * @author Joel Huamani Estefanero
 */
@NullMarked
public final class ErrorResponseAssembler {
    private static final String MESSAGES_BASENAME = "messages";

    private ErrorResponseAssembler() {}

    /**
     * Converts an ApplicationError into a ResponseEntity containing an ErrorResource. The method maps the error code to an appropriate HTTP status code and retrieves a localized message based on the error code and details.
     * @param error the ApplicationError to convert
     * @return a ResponseEntity containing an ErrorResource with the error code, localized message, and details, along with the corresponding HTTP status code
     */
    public static ResponseEntity<ErrorResource> toErrorResponseFromApplicationError(ApplicationError error) {
        HttpStatusCode status = toStatusFromErrorCode(error.code());
        String localizedMessage = toLocalizedMessageFromApplicationError(error);
        ErrorResource resource = new ErrorResource(error.code(), localizedMessage, error.details());
        return new ResponseEntity<>(resource, status);
    }

    /**
     * Retrieves a localized message for the given ApplicationError. It first attempts to find a specific localized message based on the error code. If no specific message is found, it falls back to a generic message based on the error code.
     * @param error the ApplicationError for which to retrieve a localized message
     * @return the localized message corresponding to the error code and details, or a fallback message if no specific localization is found
     */
    private static String toLocalizedMessageFromApplicationError(ApplicationError error) {
        String specificKey = toSpecificMessageKeyFromErrorCode(error.code());
        String specificMessage = toLocalizedMessageOrNull(specificKey, error.details(), toEntityNameFromErrorCode(error.code()));
        if (specificMessage != null) {
            return specificMessage;
        }
        String fallbackKey = toMessageKeyFromErrorCode(error.code());
        return toLocalizedMessageWithFallback(
                fallbackKey,
                error.message(),
                error.details(),
                toEntityNameFromErrorCode(error.code())
        );
    }

    /**
     * Converts an error code into a specific message key for localization. The method formats the error code to lowercase and replaces underscores with hyphens to create a key that can be used to look up localized messages in resource bundles.
     * @param errorCode the error code to convert into a message key
     * @return the specific message key corresponding to the error code, formatted for localization lookup
     */
    private static String toSpecificMessageKeyFromErrorCode(String errorCode) {
        return "error.%s.message".formatted(errorCode.toLowerCase(Locale.ROOT).replace('_', '-'));
    }

    /**
     * Maps an error code to a generic message key for localization. The method uses a switch statement to determine the appropriate message key based on the error code. If the error code does not match any predefined cases, it defaults to a generic error message key.
     * @param errorCode the error code to map to a message key
     * @return the generic message key corresponding to the error code, or a default key if no specific mapping exists
     */
    private static String toMessageKeyFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> "error.validation.message";
            case "BUSINESS_RULE_VIOLATION" -> "error.business-rule.message";
            case "UNEXPECTED_ERROR" -> "error.unexpected.message";
            case String s when s.endsWith("_NOT_FOUND") -> "error.not-found.message";
            case String s when s.endsWith("_CONFLICT") -> "error.conflict.message";
            default -> "error.generic.message";
        };
    }

    /**
     * Extracts the entity name from an error code for use in localized messages. The method checks if the error code ends with specific suffixes (e.g., "_NOT_FOUND" or "_CONFLICT") and removes them to derive the entity name. If no specific suffix is found, it defaults to "resource".
     * @param errorCode the error code from which to extract the entity name
     * @return the entity name derived from the error code, or "resource" if no specific entity can be determined
     */
    private static String toEntityNameFromErrorCode(String errorCode) {
        if (errorCode.endsWith("_NOT_FOUND")) {
            return errorCode.replace("_NOT_FOUND", "").toLowerCase(Locale.ROOT);
        }
        if (errorCode.endsWith("_CONFLICT")) {
            return errorCode.replace("_CONFLICT", "").toLowerCase(Locale.ROOT);
        }
        return "resource";
    }

    /**
     * Attempts to retrieve a localized message for a given key and format it with the provided arguments. If the key is not found in the resource bundle, the method returns null. This allows for graceful handling of missing localization entries without throwing exceptions.
     * @param key the key to look up in the resource bundle for localization
     * @param args the arguments to format the localized message with, if found
     * @return the formatted localized message if the key is found, or null if the key does not exist in the resource bundle
     */
    private static String toLocalizedMessageOrNull(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, locale);
            if (!bundle.containsKey(key)) {
                return null;
            }
            String template = bundle.getString(key);
            return MessageFormat.format(template, args);
        } catch (MissingResourceException ex) {
            return null;
        }
    }

    /**
     * Attempts to retrieve a localized message for a given key and format it with the provided arguments. If the key is not found in the resource bundle, the method returns a fallback message instead. This ensures that a meaningful message is always returned, even if localization entries are missing.
     * @param key the key to look up in the resource bundle for localization
     * @param fallback the fallback message to return if the key is not found in the resource bundle
     * @param args the arguments to format the localized message with, if found
     * @return the formatted localized message if the key is found, or the fallback message if the key does not exist in the resource bundle
     */
    private static String toLocalizedMessageWithFallback(String key, String fallback, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, locale);
            if (!bundle.containsKey(key)) {
                return fallback;
            }
            String template = bundle.getString(key);
            return MessageFormat.format(template, args);
        } catch (MissingResourceException ex) {
            return fallback;
        }
    }

    /**
     * Maps an error code to an appropriate HTTP status code. The method uses a switch statement to determine the corresponding HTTP status based on the error code. If the error code does not match any predefined cases, it defaults to returning HTTP 500 Internal Server Error.
     * @param errorCode the error code to map to an HTTP status code
     * @return the corresponding HttpStatusCode for the given error code, or HttpStatus.INTERNAL_SERVER_ERROR if no specific mapping exists
     */
    public static HttpStatusCode toStatusFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case String s when s.endsWith("_NOT_FOUND") -> HttpStatus.NOT_FOUND;
            case "BUSINESS_RULE_VIOLATION" -> HttpStatusCode.valueOf(422);
            case String s when s.endsWith("_CONFLICT") -> HttpStatus.CONFLICT;
            case "UNEXPECTED_ERROR" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
