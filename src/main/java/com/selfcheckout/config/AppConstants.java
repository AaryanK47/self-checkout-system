package com.selfcheckout.config;

import java.math.BigDecimal;

public final class AppConstants {

    // GST Rate (18%)
    public static final BigDecimal TAX_RATE = new BigDecimal("0.18");

    // Prevent object creation
    private AppConstants() {
    }
}