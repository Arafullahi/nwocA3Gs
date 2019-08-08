package com.nwoc.a3gs.group.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nwoc.a3gs.group.app.exceptions.EnumValidationException;

public enum ServiceStatus {

	SERVICE_REQUESTED("SERVICE_REQUESTED"),
	AMOUNT_PAYED("AMOUNT_PAYED"),
	SERVICE_APPROVED("SERVICE_APPROVED"),
	SERVICE_COMPLETED("SERVICE_COMPLETED");
	
	private final String type;

	ServiceStatus(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    @JsonCreator
    public static ServiceStatus create (String value) throws EnumValidationException {
        if(value == null) {
            throw new EnumValidationException(value, "ServiceStatus");
        }
        for(ServiceStatus v : values()) {
            if(value.equals(v.getType())) {
                return v;
            }
        }
        throw new EnumValidationException(value, "PaymentType");
    }
}
