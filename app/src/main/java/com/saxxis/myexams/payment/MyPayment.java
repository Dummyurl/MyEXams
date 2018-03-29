package com.saxxis.myexams.payment;

import android.content.Context;

import com.ebs.android.sdk.Config;
import com.ebs.android.sdk.EBSPayment;
import com.ebs.android.sdk.PaymentRequest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by saxxis25 on 10/20/2017.
 */

public class MyPayment {
    private static int ACC_ID = 24140; // Provided by EBS
    private static String SECRET_KEY = "933ce832f7216ed360f9ad0a3e1f7c67";// Provided by EBS
    private static String PAYMENT_ENVERONMENT="";
    // Mandatory
    private static String HOST_NAME = "";

    ArrayList<HashMap<String, String>> custom_post_parameters;

    public void callEbsKit(Context context, String orderId, String finalmoney) {
        /**
         * Set Parameters Before Initializing the EBS Gateway, All mandatory
         * values must be provided
         */

        /** Payment Amount Details */
        // Total Amount

        PaymentRequest.getInstance().setTransactionAmount(finalmoney);

        /** Mandatory */

        PaymentRequest.getInstance().setAccountId(ACC_ID);
        PaymentRequest.getInstance().setSecureKey(SECRET_KEY);

        // Reference No
        PaymentRequest.getInstance().setReferenceNo(orderId);
        /** Mandatory */

        // Email Id
        PaymentRequest.getInstance().setBillingEmail("test_tag@testmail.com");
        /** Mandatory */

        /**
         * Set failure id as 1 to display amount and reference number on failed
         * transaction page. set 0 to disable
         */
        PaymentRequest.getInstance().setFailureid("0");
        /** Mandatory */

        // Currency
        PaymentRequest.getInstance().setCurrency("INR");
        /** Mandatory */


        // Your Reference No or Order Id for this transaction
        PaymentRequest.getInstance().setTransactionDescription(
                orderId);

        /** Billing Details */
        PaymentRequest.getInstance().setBillingName("Test_Name");
        /** Optional */
        PaymentRequest.getInstance().setBillingAddress("North Mada Street");
        /** Optional */
        PaymentRequest.getInstance().setBillingCity("Chennai");
        /** Optional */
        PaymentRequest.getInstance().setBillingPostalCode("600019");
        /** Optional */
        PaymentRequest.getInstance().setBillingState("Tamilnadu");
        /** Optional */
        PaymentRequest.getInstance().setBillingCountry("IND");
        // ** Optional */
        PaymentRequest.getInstance().setBillingPhone("01234567890");
        /** Optional */
        /** set custom message for failed transaction */

        PaymentRequest.getInstance().setFailuremessage("Payment Failed Message");
        /** Optional */
        /** Shipping Details */
        PaymentRequest.getInstance().setShippingName("Test_Name");
        /** Optional */
        PaymentRequest.getInstance().setShippingAddress("North Mada Street");
        /** Optional */
        PaymentRequest.getInstance().setShippingCity("Chennai");
        /** Optional */
        PaymentRequest.getInstance().setShippingPostalCode("600019");
        /** Optional */
        PaymentRequest.getInstance().setShippingState("Tamilnadu");
        /** Optional */
        PaymentRequest.getInstance().setShippingCountry("IND");
        /** Optional */
        PaymentRequest.getInstance().setShippingEmail("test@testmail.com");
        /** Optional */
        PaymentRequest.getInstance().setShippingPhone("01234567890");
        /** Optional */
		/* enable log by setting 1 and disable by setting 0 */
        PaymentRequest.getInstance().setLogEnabled("0");

        /**
         * Initialise parameters for dyanmic values sending from merchant custom
         * values from merchant
         */

        custom_post_parameters = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hashpostvalues = new HashMap<String, String>();
        hashpostvalues.put("account_details", "saving");
        hashpostvalues.put("merchant_type", "gold");
        custom_post_parameters.add(hashpostvalues);

        PaymentRequest.getInstance()
                .setCustomPostValues(custom_post_parameters);
        /** Optional-Set dyanamic values */

        // PaymentRequest.getInstance().setFailuremessage(getResources().getString(R.string.payment_failure_message));

//        if(PAYMENT_ENVERONMENT.equals("LIVE")){
//            EBSPayment.getInstance().init(context, ACC_ID, SECRET_KEY,
//                    Config.Mode.ENV_LIVE, Config.Encryption.ALGORITHM_SHA512, HOST_NAME);
//        }
//        if (PAYMENT_ENVERONMENT.equals("TEST")){
            EBSPayment.getInstance().init(context, ACC_ID, SECRET_KEY,
                    Config.Mode.ENV_TEST, Config.Encryption.ALGORITHM_SHA512, HOST_NAME);
        }

        // EBSPayment.getInstance().init(context, accId, secretkey, environment,
        // algorithm, host_name);


}
