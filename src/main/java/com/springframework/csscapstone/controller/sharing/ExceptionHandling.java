package com.springframework.csscapstone.controller.sharing;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.security.model.model_exception.HttpResponse;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.security.auth.login.AccountNotFoundException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    /**
     * TODO Not Found Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler({
            AccountNotFoundException.class,
            ProductNotFoundException.class,
            CategoryNotFoundException.class
    })
    public ResponseEntity<?> notFoundException(RuntimeException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }
    // ================== handler exception ===============================
    // ================== Login was wrong =================================

    /**
     * TODO Forbidden for Authentication Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationException(AuthenticationException exception) {
        String message = exception.getMessage();
        if (exception.getMessage().equals("Bad credentials")) {
            message = "The username or password was wrong";
        }
        return createHttpResponse(FORBIDDEN, message);
    }
    // ================== handler exception ===============================
    // ================== Argument was wrong ==============================

    /**
     * TODO Bad Exception Handling
     * @param exception
     * @return
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            AccountExistException.class,
            AccountInvalidException.class,
            CampaignInvalidException.class,
            ProductInvalidException.class,
            CategoryInvalidException.class,
            FirebaseAuthException.class
    })
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
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
    public ResponseEntity<?> jwtVerificationException(JWTDecodeException exception) {
        return createHttpResponse(FORBIDDEN, exception.getMessage());
    }

    // ================== handler exception ================================
    // ================== UTILS ============================================
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(new HttpResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), message), status);
    }
}
