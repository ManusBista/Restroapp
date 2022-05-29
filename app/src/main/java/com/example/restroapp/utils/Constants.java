package com.example.restroapp.utils;


import com.example.restroapp.BuildConfig;

public interface Constants {

    String BASE_API = BuildConfig.BASE_API;
    String GET_ORDER_DETAILS_BY_ID_FOR_TAB = BASE_API + "MerchantRestaurant/GetOrderDetailsByIdForTab";

    String INSERT_CUSTOMER_DETAILS = BASE_API + "MerchantRestaurant/InsertCustomerDetails";

    String GET_ACTIVE_ORDER_DETAILS = BASE_API + "MerchantRestaurant/GetActiveOrderDetails";

    String SEND_MSG_TO_CUSTOMERS = BASE_API + "MerchantRestaurant/SendMsgToCustomers";

}
