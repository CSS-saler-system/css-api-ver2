package com.springframework.csscapstone.functionalinterface;

@FunctionalInterface
public interface CheckedException<T,R> {
    R apply(T t) throws RuntimeException;
}
