package com.nwoc.a3gs.group.app.braintree;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class BraintreeProperties {

	 	@Value("${BT_ENVIRONMENT}")
	    private String environment;

	    @Value("${BT_MERCHANT_ID}")
	    private String merchantId;
	    
	    @Value("${BT_PUBLIC_KEY}")
	    private String publicKey;

	    @Value("${BT_PRIVATE_KEY}")
	    private String privateKey;

		public String getEnvironment() {
			return environment;
		}

		public void setEnvironment(String environment) {
			this.environment = environment;
		}

		public String getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
	    
	    
}
