package com.springframework.csscapstone.config.message.constant;

public class MessageConstant {

    public static final String REQUEST_SUCCESS = "request.success.message";
    public static final String REQUEST_FAILURE = "request.false.message";
    public static final String KPI_NOT_ENOUGH = "kpi.error.not_enough";
    public static class Prize {

        public static final String BAD_JSON = "prize.error.bad_json";
        public static final String NOT_FOUND = "prize.error.not_found";
    }

    public static class  DATA {
       public static final String INVALID = "data.error.temp";
        public static final String PRODUCT_NOT_BELONG_ACCOUNT = "data.error.belonging";
    }

    public static class Point {

        public static final String LACK_POINT = "point.error.lack";
    }


    public static class Exception {
        public static final String JSON_ERROR = "json.error.no_readable";
        public static final String MAX_SIZE_FILE = "file.error.maz_size";
    }


    public static class Auth {
        public static final String ACCESS_DENIED_MESSAGE = "request.false.auth-access-denied";
        //You need to log in to access this page
        public static final String FORBIDDEN_MESSAGE = "request.false.auth-forbidden";
    }


    public static class Category {

        public static final String NOT_FOUND = "category.error.not-found";
        public static final String INVALID = "category.error.invalid";
    }

    public static class Product {
        public static final String NOT_FOUND = "product.error.not-found";
        public static final String INVALID = "product.error.invalid";

    }

    public static class Campaign {
        public static final String NOT_FOUND = "campaign.error.not-found";
        public static final String INVALID = "campaign.error.invalid";
    }

    public static class Account {
        public static final String NOT_FOUND = "account.error.not-found";
        public static final String INVALID = "account.error.invalid";
        public static final String EXISTED = "account.error.existed";
        public static final String EMAIL_EXITED = "account.error.email_exited";
        public static final String USER_NAME_EXISTED = "account.error.username_existed";
        public static final String PHONE_EXISTED = "account.error.phone_exited";
        public static final String FAIL_LOGIN_EMAIL = "account.error.email_not_existed";
        public static final String CREATOR_NOT_FOUND = "account.error.creator_not_found";
        public static final String EMAIL_NOT_BLANK = "account.error.email_not_empty";

    }

    public static class Order {
        public static final String NOT_FOUND = "order.error.not_found";
    }

    public static class OrderDetail {
        public static final String CANT_CREATE = "order_detail.error.cant_create";
        public static final String NOT_FOUND = "order_detail.error.not_found";
    }

    public static class Customer {
        public static final String NOT_FOUND = "customer.error.not-found";
        public static final String EXISTED = "customer.error.existed";
    }

    public static class RequestSellingProduct {
        public static final String NOT_FOUND = "request_selling_product.error.not_found";
        public static final String NOT_CREATE_STATUS = "request_selling_product.error.not_create";
    }


}
