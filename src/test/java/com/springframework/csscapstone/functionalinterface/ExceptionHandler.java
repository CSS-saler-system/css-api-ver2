package com.springframework.csscapstone.functionalinterface;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface ExceptionHandler<E, S> {
    S jsonExceptionHandler(E e) throws JsonProcessingException;

}
