package com.nwoc.a3gs.group.app.braintree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

public class BraintreeGatewayFactory {

	public static BraintreeGateway fromConfigMapping(Map<String, String> mapping) {
        return new BraintreeGateway(
            mapping.get("BT_ENVIRONMENT"),
            mapping.get("BT_MERCHANT_ID"),
            mapping.get("BT_PUBLIC_KEY"),
            mapping.get("BT_PRIVATE_KEY")
        );
    }

	public static BraintreeGateway fromBrainTreeProperties(BraintreeProperties properties) {
		BraintreeGateway gateway = new BraintreeGateway(properties.getEnvironment(),
  			  properties.getMerchantId(),
  			  properties.getPublicKey(),
  			  properties.getPrivateKey()
  			);
  	return gateway;
    }
 
    public static BraintreeGateway fromConfigFile(File configFile) {
        InputStream inputStream = null;
        Properties properties = new Properties();
 
        try {
            inputStream = new FileInputStream(configFile);
            properties.load(inputStream);
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        } finally {
            try { inputStream.close(); }
            catch (IOException e) { System.err.println("Exception: " + e); }
        }
 
        return new BraintreeGateway(
            properties.getProperty("BT_ENVIRONMENT"),
            properties.getProperty("BT_MERCHANT_ID"),
            properties.getProperty("BT_PUBLIC_KEY"),
            properties.getProperty("BT_PRIVATE_KEY")
        );
    }
}
