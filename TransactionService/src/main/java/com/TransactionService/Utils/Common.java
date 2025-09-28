package com.TransactionService.Utils;

import java.security.SecureRandom;
import java.util.Base64;

public class Common {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateToken(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static double getTransactionLimit(Long user_id){
        //make request to user to fetchLimit
        return 50000.00;
    }

    public static Boolean pinVerify(String pin){
        return true;
    }

    public static void notificationNotify(String title, String message){
       //call notification service
    }

//    public static Object processPayment(){
//
//    }

}
