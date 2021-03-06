package com.springframework.csscapstone.controller.sharing;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.payload.response_dto.exception.HttpResponse;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.InvalidCampaignAndProductException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.NotEnoughKpiException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.customer_exception.CustomerExistedException;
import com.springframework.csscapstone.utils.exception_utils.customer_exception.CustomerNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.data_exception.DataTempException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.transaction_exceptions.TransactionNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.UnexpectedTypeException;

import java.time.format.DateTimeParseException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    /**
     * TODO handle invalid DTO
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        LOGGER.error("The Exception was throwing");
        HttpResponse httpResponse = new HttpResponse(BAD_REQUEST);
        httpResponse.setMessage("Validation Error");
        httpResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
        httpResponse.addValidationError(ex.getBindingResult().getGlobalErrors());
        return this.buildResponseEntity(httpResponse);
    }

    /**
     * TODO handle request is not readable
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        HttpResponse httpResponse = new HttpResponse(BAD_REQUEST, error, ex);
        return buildResponseEntity(httpResponse);
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<?> handleFileSizeLimitExceededException(FileSizeLimitExceededException exception) {
        return createHttpResponse(BAD_REQUEST, MessagesUtils.getMessage(MessageConstant.Exception.MAX_SIZE_FILE));
    }

    /**
     * TODO backend error wrong type constraints validation
     * @param exception
     * @return
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException exception) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setDebugMessage(exception.getLocalizedMessage());
        httpResponse.setHttpStatus(BAD_REQUEST);
        return new ResponseEntity<>(httpResponse, BAD_REQUEST);
    }


    /**
     * TODO Not Found Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler({
            ProductNotFoundException.class,
            CategoryNotFoundException.class,
            CategoryNotFoundException.class,
            EntityNotFoundException.class,
            CustomerNotFoundException.class,
            CampaignNotFoundException.class,
            NotEnoughKpiException.class,
            TransactionNotFoundException.class
    })
    public ResponseEntity<?> handleNotFound(RuntimeException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    /**
     * TODO Forbidden for Authentication Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleUnAuthentication(AuthenticationException exception) {
        String message = exception.getMessage();
        if (exception.getMessage().equals("Bad credentials")) {
            message = "The email or password was wrong";
        }
        return createHttpResponse(FORBIDDEN, message);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * TODO Bad Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler({
            AccountExistException.class,
            AccountInvalidException.class,
            CampaignInvalidException.class,
            ProductInvalidException.class,
            CategoryInvalidException.class,
            CustomerExistedException.class,
            DataTempException.class,
            InvalidCampaignAndProductException.class,
            RuntimeException.class
    })
    public ResponseEntity<?> handleMethodArgNotValid(RuntimeException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * TODO Forbidden Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler({
            JWTDecodeException.class,
            AccountLoginWithEmailException.class,
    })
    public ResponseEntity<?> handleJWTInvalid(JWTDecodeException exception) {
        return createHttpResponse(FORBIDDEN, exception.getMessage());
    }

    /**
     * TODO String JSON was malformations
     * @param exception
     * @return
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException exception) {
        return createHttpResponse(BAD_REQUEST, MessagesUtils.getMessage(MessageConstant.Exception.JSON_ERROR));
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<?> handleFirebaseAuthException(FirebaseAuthException exception) {
        return createHttpResponse(FORBIDDEN, exception.getMessage());
    }

    // ================== handler exception ================================
    // ================== UTILS ============================================
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(new HttpResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), message), status);
    }
    private ResponseEntity<Object> buildResponseEntity(HttpResponse httpResponse) {
        return new ResponseEntity<>(httpResponse, httpResponse.getHttpStatus());
    }
}
