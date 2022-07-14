package com.springframework.csscapstone.config.constant;

public class ApiEndPoint {
    public static final String ADMIN = "/api/v1.0/admin";
    public static final String ENTERPRISE = "/api/v2.0/enterprise";
    public static final String COLLABORATOR = "/api/v3.0/collaborator";
    public static final String MODERATOR = "/api/v4.0/moderator";

    public static final String USER = "/user";

    public static final String USER_OPEN_LOGIN = "user/open-login";
    public static final String USER_LOGOUT = "/logout";
    public static final String ADMIN_LOGIN = ADMIN + "/login";
    public static final String COLLABORATOR_LOGIN = COLLABORATOR + "/login";
    public static final String ENTERPRISE_LOGIN = ENTERPRISE + "/login";
    public static final String ENTERPRISE_SIGNUP = ENTERPRISE + "/singup";

    public static final String MODERATOR_LOGIN = MODERATOR + "/login";

    static class AdminConfiguration {
        private static final String v1_handler = ADMIN + "/config";
    }

    public static class Product {
        private static final String v1_handler = ADMIN + "/product";
        private static final String v2_handler = ENTERPRISE + "/product";
        private static final String v3_handler = COLLABORATOR + "/product";
        private static final String v4_handler = MODERATOR + "/product";

        public static final String V2_PRODUCT_LIST = v2_handler + "/list";
        public static final String V2_PRODUCT_COUNT_LIST = v2_handler + "/count-sold/list";
        public static final String V2_PRODUCT_GET_BY_CATEGORY = v2_handler + "/by-category/list";
        public static final String V2_PRODUCT_GET = v2_handler + "/get";
        public static final String V2_PRODUCT_CREATE = v2_handler + "/new";
        public static final String V2_PRODUCT_DELETE = v2_handler + "/delete";
        public static final String V2_PRODUCT_UPDATE = v2_handler + "/update";

        public static final String V3_LIST_PRODUCT = v3_handler + "/list";
        public static final String V3_GET_PRODUCT = v3_handler + "/get";
        public static final String V3_CREATE_PRODUCT = v3_handler + "/new";
        public static final String V3_DELETE_PRODUCT = v3_handler + "/delete";
        public static final String V3_UPDATE_PRODUCT = v3_handler + "/update";

        public static final String V4_LIST_PRODUCT = v4_handler + "/list";
        public static final String V4_GET_PRODUCT = v4_handler + "/get";
        public static final String V4_CREATE_PRODUCT = v4_handler + "/new";
        public static final String V4_DELETE_PRODUCT = v4_handler + "/delete";

        public static final String V4_ACTIVE_PRODUCT = v4_handler + "/active";
        public static final String V4_DISABLE_PRODUCT = v4_handler + "/disable";
    }

    public static class Account {
        public static final String v1_handler = ADMIN + "/account";
        public static final String v2_handler = ENTERPRISE + "/collaborator";
        public static final String v3_handler = COLLABORATOR + "/customer";
        public static final String v4_handler = MODERATOR + "/enterprise";


        public static final String V1_LIST_ACCOUNT = v1_handler + "/list";
        public static final String V1_GET_ACCOUNT = v1_handler + "/get";
        public static final String V1_CREATE_ACCOUNT = v1_handler + "/new";
        public static final String V1_DELETE_ACCOUNT = v1_handler + "/delete";
        public static final String V1_UPDATE_ACCOUNT = v1_handler + "/update";

        public static final String V2_COLLABORATOR_LIST = v2_handler + "/list";
        public static final String V2_ORDER_COLLABORATOR_LIST = v2_handler + "/count-order/list";
        public static final String V2_COLLABORATOR_PERFORMANCE = v2_handler + "/performance/retrieve";

        public static final String V2_COLLABORATOR_CAMPAIGN_LIST = v2_handler + "/campaign-join/list";


        public static final String V3_LIST_ACCOUNT = v3_handler + "/enterprise/list";
        public static final String V3_UPDATE_ACCOUNT = v3_handler + "/update";

        public static final String V4_LIST_ACCOUNT = v4_handler + "/list";
        public static final String V4_GET_ACCOUNT = v4_handler + "/get";
        public static final String V4_ACCOUNT_UPDATE = v4_handler + "/update";

        public static final String V4_ACCOUNT_CREATE = v4_handler + "/new";
        public static final String V4_DELETE_ACCOUNT = v4_handler + "/delete";
        public static final String V4_ACCOUNT_GET = v4_handler + "/get";
    }


    public static class Campaign {
        public static final String v1_handler = ADMIN + "/campaign";
        public static final String v2_handler = ENTERPRISE + "/campaign";
        public static final String v3_handler = COLLABORATOR + "/campaign";
        public static final String v4_handler = MODERATOR + "/campaign";


        public static final String V2_CAMPAIGN_LIST = v2_handler + "/list";
        public static final String V2_GET_CAMPAIGN = v2_handler + "/get";
        public static final String V2_CAMPAIGN_UPDATE = v2_handler + "/update";
        public static final String V2_CAMPAIGN_CREATE = v2_handler + "/new";
        public static final String V2_CAMPAIGN_GET = v2_handler + "/get";
        public static final String V2_CAMPAIGN_SENT = v2_handler + "/sent";
        public static final String V2_CAMPAIGN_DELETE = v2_handler + "/delete";
        public static final String V3_LIST_CAMPAIGN = v3_handler + "/list";
        public static final String V3_GET_CAMPAIGN = v3_handler + "/get";
        public static final String V3_CAMPAIGN_UPDATE = v3_handler + "/update";
        public static final String V3_CAMPAIGN_CREATE = v3_handler + "/new";
        public static final String V3_CAMPAIGN_DELETE = v3_handler + "/delete";

        public static final String V4_LIST_CAMPAIGN = v4_handler + "/list";
        public static final String V4_GET_CAMPAIGN = v4_handler + "/get";
        public static final String V4_UPDATE_CAMPAIGN = v4_handler + "/update";
        public static final String V4_CREATE_CAMPAIGN = v4_handler + "/new";
        public static final String V4_REJECT_CAMPAIGN = v4_handler + "/reject";
        public static final String V4_APPROVAL_CAMPAIGN = v4_handler + "/approve";
        public static final String V4_DELETE_CAMPAIGN = v4_handler + "/delete";
    }

    public static class Category {
        public static final String v1_handler = ADMIN + "/category";
        public static final String v2_handler = ENTERPRISE + "/category";
        public static final String v3_handler = COLLABORATOR + "/category";
        public static final String v4_handler = MODERATOR + "/category";


        public static final String V2_CATEGORY_LIST = v2_handler + "/list";
        public static final String V2_CATEGORY_GET = v2_handler + "/get";
        public static final String V2_CATEGORY_UPDATE = v2_handler + "/update";
        public static final String V2_CATEGORY_CREATE = v2_handler + "/new";
        public static final String V2_CATEGORY_DELETE = v2_handler + "/delete";

        public static final String V3_LIST_CATEGORY = v3_handler + "/list";
        public static final String V3_GET_CATEGORY = v3_handler + "/get";
        public static final String V3_UPDATE_CATEGORY = v3_handler + "/update";
        public static final String V3_CREATE_CATEGORY = v3_handler + "/new";
        public static final String V3_DELETE_CATEGORY = v3_handler + "/delete";

        public static final String V4_LIST_CATEGORY = v4_handler + "/list";
        public static final String V4_GET_CATEGORY = v4_handler + "/get";
        public static final String V4_UPDATE_CATEGORY = v4_handler + "/update";
        public static final String V4_CREATE_CATEGORY = v4_handler + "/new";
        public static final String V4_DELETE_CATEGORY = v4_handler + "/delete";
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

        public static final String V3_LIST_ORDER_DETAIL = v3_handler + "/list";
        public static final String V3_GET_ORDER_DETAIL = v3_handler + "/get";
        public static final String V3_UPDATE_ORDER_DETAIL = v3_handler + "/update";
        public static final String V3_CREATE_ORDER_DETAIL = v3_handler + "/new";
        public static final String V3_DELETE_ORDER_DETAIL = v3_handler + "/delete";


    }

    public static class Customer {
        private static final String v1_handler = ADMIN + "/customer";
        private static final String v2_handler = ENTERPRISE + "/customer";
        private static final String v3_handler = COLLABORATOR + "/customer";

        public static final String V3_LIST_CUSTOMER = v3_handler + "/list";
        public static final String V3_GET_CUSTOMER_BY_PHONE = v3_handler + "/retrieve";
        public static final String V3_GET_CUSTOMER = v3_handler + "/get";
        public static final String V3_UPDATE_CUSTOMER = v3_handler + "/update";
        public static final String V3_CREATE_CUSTOMER = v3_handler + "/new";
    }

    public static class RequestSellingProduct {
        private static final String v1_handler = ADMIN + "/request_selling";
        private static final String v2_handler = ENTERPRISE + "/request_selling";
        private static final String v3_handler = COLLABORATOR + "/request_selling";
        private static final String v4_handler = MODERATOR + "/request_selling";

        public static final String V2_REQUEST_LIST = v2_handler + "/enterprise/list";
        public static final String V2_REQUEST_GET = v2_handler + "/get";
        public static final String V2_REQUEST_UPDATE = v2_handler + "/accept";
        public static final String V3_REQUEST_CREATE = v3_handler + "/create";
        public static final String V3_LIST_REQUEST = v3_handler + "/list";
        public static final String V3_REQUEST_GET_OWNER = v3_handler + "/get";
        public static final String V3_REQUEST_RETRIEVE = v3_handler + "/retrieve";

//        public static final String V4_LIST_REQUEST = v4_ha
    }

    public static class Order {
        private static final String v1_handler = ADMIN + "/order";
        private static final String v2_handler = ENTERPRISE + "/order";
        private static final String v3_handler = COLLABORATOR + "/order";

        public static final String V2_ORDER_UPDATE_STATUS = v2_handler + "/order";
        public static final String V2_ORDER_COMPLETE = v2_handler + "/complete";
        public static final String V2_ORDER_LIST_BY_ENTERPRISE = v2_handler + "/list";
        public static final String  V2_ORDER_GET = v2_handler + "/get";
        public static final String V2_REVENUE_DASHBOARD = v2_handler + "/revenue";
        public static final String V3_ORDER_CREATE = v3_handler + "/new";
        public static final String V3_ORDER_GET = v3_handler + "/get";
        public static final String V3_ORDER_LIST = v3_handler + "/list";
        public static final String V3_ORDER_UPDATE = v3_handler + "/update";
        public static final String V3_ORDER_DELETE = v3_handler + "/delete";


    }

    public static class Prize {
        private static final String v1_handler = ADMIN + "/prize";
        private static final String v2_handler = ENTERPRISE + "/prize";
        private static final String v3_handler = COLLABORATOR + "/prize";

        public static final String V2_PRIZE_LIST = v2_handler + "/list";
        public static final String V2_PRIZE_GET = v2_handler + "/get";
        public static final String V2_PRIZE_RETRIEVE = v2_handler + "/retrieve";
        public static final String V2_PRIZE_UPDATE = v2_handler + "/update";
        public static final String V2_PRIZE_CREATE = v2_handler + "/create";

    }

    public static class Transaction {
        private static final String v1_handler = ADMIN + "/transaction";
        private static final String v2_handler = ENTERPRISE + "/transaction";
        private static final String v3_handler = COLLABORATOR + "/transaction";
        private static final String v4_handler = MODERATOR + "/transaction";

        public static final String V2_TRANSACTION_LIST = v2_handler + "/list";
        public static final String V2_TRANSACTION_GET = v2_handler + "/get";
        public static final String V2_TRANSACTION_RETRIEVE = v2_handler + "/retrieve";
        public static final String V2_TRANSACTION_REJECT = v2_handler + "/reject";
        public static final String V2_TRANSACTION_UPDATE = v2_handler + "/update";
        public static final String V2_TRANSACTION_CREATE = v2_handler + "/create";
        public static final String V2_TRANSACTION_DELETE = v2_handler + "/delete";

        public static final String V4_TRANSACTION_LIST = v4_handler + "/list";
        public static final String V4_TRANSACTION_GET = v4_handler + "/get";
        public static final String V4_TRANSACTION_PENDING = v4_handler + "/list-pending";
        public static final String V4_TRANSACTION_ACCEPT = v4_handler + "/accept";
        public static final String V4_TRANSACTION_REJECT = v4_handler + "/reject";

    }

}
