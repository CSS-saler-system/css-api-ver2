package com.springframework.csscapstone.config.constant;

public class ApiEndPoint {
    public static final String ADMIN = "/api/v1.0/admin";
    public static final String ENTERPRISE = "/api/v2.0/enterprise";
    public static final String COLLABORATOR = "/api/v3.0/collaborator";

    public static final String USER = "/user";
    public static final String USER_REGISTER = "/user/register";
    public static final String USER_LOGIN = "/user/login";
    public static final String USER_LOGOUT = "/user/logout";
    public static final String USER_OPEN_LOGIN = "user/open-login";

    static class AdminConfiguration {
        private static final String v1_handler = ADMIN + "/config";
    }

    public static class Product {
        private static final String v1_handler = ADMIN + "/product";
        private static final String v2_handler = ENTERPRISE + "/product";
        private static final String v3_handler = COLLABORATOR + "/product";


        public static final String V1_LIST_PRODUCT = v1_handler + "/list";
        public static final String V1_GET_PRODUCT = v1_handler + "/get";
        public static final String V1_CREATE_PRODUCT = v1_handler + "/new";
        public static final String V1_DELETE_PRODUCT = v1_handler + "/delete";

        public static final String V1_ACTIVE_PRODUCT = v1_handler + "/active";

        public static final String V2_LIST_PRODUCT = v2_handler + "/list";
        public static final String V2_GET_PRODUCT = v2_handler + "/get";
        public static final String V2_CREATE_PRODUCT = v2_handler + "/new";
        public static final String V2_DELETE_PRODUCT = v2_handler + "/delete";
        public static final String V2_UPDATE_PRODUCT = v2_handler + "/update";

        public static final String V3_LIST_PRODUCT = v3_handler + "/list";
        public static final String V3_GET_PRODUCT = v3_handler + "/get";
        public static final String V3_CREATE_PRODUCT = v3_handler + "/new";
        public static final String V3_DELETE_PRODUCT = v3_handler + "/delete";
        public static final String V3_UPDATE_PRODUCT = v3_handler + "/update";
    }

    public static class Account {
        public static final String v1_handler = ADMIN + "/account";
        public static final String v2_handler = ENTERPRISE + "/account";
        public static final String v3_handler = COLLABORATOR + "/account";


        public static final String V1_LIST_ACCOUNT = v1_handler + "/list";
        public static final String V1_GET_ACCOUNT = v1_handler + "/get";
        public static final String V1_CREATE_ACCOUNT = v1_handler + "/new";
        public static final String V1_DELETE_ACCOUNT = v1_handler + "/delete";
        public static final String V1_UPDATE_ACCOUNT = v1_handler + "/update";

        public static final String V2_LIST_ACCOUNT = v2_handler + "/list";
        public static final String V2_GET_ACCOUNT = v2_handler + "/get";
        public static final String V2_CREATE_ACCOUNT = v2_handler + "/new";
        public static final String V2_DELETE_ACCOUNT = v2_handler + "/delete";
        public static final String V2_UPDATE_ACCOUNT = v2_handler + "/update";

        public static final String V3_LIST_ACCOUNT = v3_handler + "/list";
        public static final String V3_GET_ACCOUNT = v3_handler + "/get";
        public static final String V3_CREATE_ACCOUNT = v3_handler + "/new";
        public static final String V3_DELETE_ACCOUNT = v3_handler + "/delete";
        public static final String V3_UPDATE_ACCOUNT = v3_handler + "/update";
    }


    public static class Campaign {
        public static final String v1_handler = ADMIN + "/campaign";
        public static final String v2_handler = ENTERPRISE + "/campaign";
        public static final String v3_handler = COLLABORATOR + "/campaign";


        public static final String V1_LIST_CAMPAIGN = v1_handler + "/list";
        public static final String V1_GET_CAMPAIGN = v1_handler + "/get";
        public static final String V1_UPDATE_CAMPAIGN = v1_handler + "/update";
        public static final String V1_CREATE_CAMPAIGN = v1_handler + "/new";
        public static final String V1_DELETE_CAMPAIGN = v1_handler + "/delete";

        public static final String V2_LIST_CAMPAIGN = v2_handler + "/list";
        public static final String V2_GET_CAMPAIGN = v2_handler + "/get";
        public static final String V2_UPDATE_CAMPAIGN = v2_handler + "/update";
        public static final String V2_CREATE_CAMPAIGN = v2_handler + "/new";
        public static final String V2_DELETE_CAMPAIGN = v2_handler + "/delete";

        public static final String V3_LIST_CAMPAIGN = v3_handler + "/list";
        public static final String V3_GET_CAMPAIGN = v3_handler + "/get";
        public static final String V3_UPDATE_CAMPAIGN = v3_handler + "/update";
        public static final String V3_CREATE_CAMPAIGN = v3_handler + "/new";
        public static final String V3_DELETE_CAMPAIGN = v3_handler + "/delete";
    }

    public static class Category {
        public static final String v1_handler = ADMIN + "/category";
        public static final String v2_handler = ENTERPRISE + "/category";
        public static final String v3_handler = COLLABORATOR + "/category";


        public static final String V1_LIST_CATEGORY = v1_handler + "/list";
        public static final String V1_GET_CATEGORY = v1_handler + "/get";
        public static final String V1_UPDATE_CATEGORY = v1_handler + "/update";
        public static final String V1_CREATE_CATEGORY = v1_handler + "/new";
        public static final String V1_DELETE_CATEGORY = v1_handler + "/delete";

        public static final String V2_LIST_CATEGORY = v2_handler + "/list";
        public static final String V2_GET_CATEGORY = v2_handler + "/get";
        public static final String V2_UPDATE_CATEGORY = v2_handler + "/update";
        public static final String V2_CREATE_CATEGORY = v2_handler + "/new";
        public static final String V2_DELETE_CATEGORY = v2_handler + "/delete";

        public static final String V3_LIST_CATEGORY = v3_handler + "/list";
        public static final String V3_GET_CATEGORY = v3_handler + "/get";
        public static final String V3_UPDATE_CATEGORY = v3_handler + "/update";
        public static final String V3_CREATE_CATEGORY = v3_handler + "/new";
        public static final String V3_DELETE_CATEGORY = v3_handler + "/delete";
    }

    public static class OrderDetail {
        private static final String v1_handler = ADMIN + "/order-detail";
        private static final String v2_handler = ENTERPRISE + "/order-detail";
        private static final String v3_handler = COLLABORATOR + "/order-datail";

        public static final String V2_LIST_ORDER_DETAIL = v2_handler + "/list";
        public static final String V2_GET_ORDER_DETAIL = v2_handler + "/get";
        public static final String V2_UPDATE_ORDER_DETAIL = v2_handler + "/update";
        public static final String V2_CREATE_ORDER_DETAIL = v2_handler + "/new";
        public static final String V2_DELETE_ORDER_DETAIL = v2_handler + "/delete";


    }
    public static class Customer {
        private static final String v1_handler = ADMIN + "/customer";
        private static final String v2_handler = ENTERPRISE + "/customer";
        private static final String v3_handler = COLLABORATOR + "/customer";

        public static final String V3_LIST_CUSTOMER = v3_handler + "/list";
        public static final String V3_GET_CUSTOMER_BY_PHONE = v3_handler +  "/retrieve";
        public static final String V3_GET_CUSTOMER = v3_handler + "/get";
        public static final String V3_UPDATE_CUSTOMER = v3_handler + "/update";
        public static final String V3_CREATE_CUSTOMER = v3_handler + "/new";
    }


}
