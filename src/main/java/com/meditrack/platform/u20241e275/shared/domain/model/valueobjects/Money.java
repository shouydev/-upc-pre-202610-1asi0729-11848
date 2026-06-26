package com.meditrack.platform.u20241e275.shared.domain.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value Object representing a monetary amount. It encapsulates a BigDecimal to ensure precision and provides
 * basic operations for addition, subtraction, and multiplication.
 * @param value The monetary amount represented as a BigDecimal. Must be non-null and non-negative.
 * @param currency The currency of the money. Must be non-null and non-blank.
 * @author Joel Huamani Estefanero
 */
public record Money(BigDecimal value, String currency) {
    private static final String NOT_NULL_MESSAGE_KEY = "shared.error.money.required";
    private static final String NOT_NEGATIVE_MESSAGE_KEY = "shared.error.money.notNegative";
    private static final String INVALID_CURRENCY_MESSAGE = "shared.error.money.invalid";
    private static final String CURRENCY_NOT_MATCH = "shared.error.money.notMatch";

    /**
     * Constructor for Money. Validates that the value is non-null and non-negative, and sets the scale to 2 decimal places.
     * It also validates that currency is not null or blank.
     */
    public Money {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(NOT_NEGATIVE_MESSAGE_KEY);
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException(INVALID_CURRENCY_MESSAGE);
        }

        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Adds another Money instance to this one and returns a new Money instance with the result.
     */
    public Money plus(Money other) {
        if (other == null) return this;
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(CURRENCY_NOT_MATCH);
        }
        return new Money(this.value.add(other.value), this.currency);
    }

    /**
     * Subtracts another Money instance from this one and returns a new Money instance with the result.
     */
    public Money minus(Money other) {
        if (other == null) return this;
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(CURRENCY_NOT_MATCH);
        }
        return new Money(this.value.subtract(other.value), this.currency);
    }

    /**
     * Multiplies this Money instance by a given quantity and returns a new Money instance with the result.
     */
    public Money multiply(int quantity) {
        return new Money(this.value.multiply(BigDecimal.valueOf(quantity)), this.currency);
    }

    /**
     * Multiplies this Money instance by a given factor and returns a new Money instance with the result.
     */
    public Money multiply(BigDecimal factor) {
        if (factor == null) return this;
        return new Money(this.value.multiply(factor), this.currency);
    }

    /**
     * Applies a percentage discount to this Money instance and returns a new Money instance with the discounted value and the same currency.
     * @param percentage The percentage to discount (e.g., 20 for 20% discount).
     * @return A new Money instance representing the value after applying the discount.
     */
    public Money applyDiscount(BigDecimal percentage) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException(CURRENCY_NOT_MATCH);
        }
        BigDecimal discountFactor = BigDecimal.ONE.subtract(percentage.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
        BigDecimal discountedValue = this.value.multiply(discountFactor);
        return new Money(discountedValue, this.currency);
    }

    /**
     * Compares this Money instance to another and returns true if this value is greater than the other value.
     */
    public boolean isGreaterThan(Money other) {
        return other != null && this.currency.equals(other.currency) && this.value.compareTo(other.value) > 0;
    }

    /**
     * Compares this Money instance to another and returns true if this value is less than the other value.
     */
    public boolean isLessThan(Money other) {
        return other != null && this.currency.equals(other.currency) && this.value.compareTo(other.value) < 0;
    }
}