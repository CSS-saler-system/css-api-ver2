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

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    // ================== handler exception ===============================
    // ================== Login was wrong =================================
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    // ================== handler exception ===============================
    // ================== Account =========================================
    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<HttpResponse> accountExistException(AccountExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AccountInvalidException.class)
    public ResponseEntity<HttpResponse> accountInvalidException(AccountInvalidException exception) {
        exception.printStackTrace();
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    // ================== handler exception ================================
    // ================== Campaign =========================================
//
//    @ExceptionHandler(CampaignNotFoundException.class)
//    public ResponseEntity<HttpResponse> campaignNotFoundException(CampaignNotFoundException exception) {
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }



    @ExceptionHandler(CampaignInvalidException.class)
    public ResponseEntity<HttpResponse> campaignInvalidException(CampaignInvalidException exception) {
        exception.printStackTrace();
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    // ================== handler exception ================================
    // ================== Campaign =========================================
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HttpResponse> productNotFoundException(ProductNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ProductInvalidException.class)
    public ResponseEntity<HttpResponse> productNotFoundException(ProductInvalidException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    // ================== handler exception ================================
    // ================== Category =========================================
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<HttpResponse> categoryNotFoundException(CategoryNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(CategoryInvalidException.class)
    public ResponseEntity<HttpResponse> categoryNotFoundException(CategoryInvalidException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    //===========================Common Exception=================================
    //============================================================================
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<?> jwtVerificationException(JWTDecodeException exception) {
        return createHttpResponse(FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<?> firebaseAuthException(FirebaseAuthException exception) {

        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AccountLoginWithEmailException.class)
    public ResponseEntity<?> authenticationException(AccountLoginWithEmailException exception) {
        return createHttpResponse(FORBIDDEN, exception.getMessage());
    }

    // ================== handler exception ================================
    // ================== UTILS ============================================
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(new HttpResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), message), status);
    }
}
