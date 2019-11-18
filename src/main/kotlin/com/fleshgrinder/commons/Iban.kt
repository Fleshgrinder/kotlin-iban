@file:Suppress("RedundantVisibilityModifier", "unused")

package com.fleshgrinder.commons

public class Iban(value: String) {
    public val value: String

    public inline val country: String
        get() = value.substring(0, 2)

    public inline val checkDigit: Int
        get() = value.substring(2, 4).toInt()

    public inline val basicBankAccountNumber: String
        get() = value.substring(4, value.length)

    init {
        this.value = value
    }

    public fun toDisplayString(): String =
        TODO()

    public override fun toString(): String =
        value

    private class Country(
        val checkDigits: Int,
        val sepa: Boolean
    )

    public companion object {
        // TODO the library and its data should be two separate artifacts.
        private val COUNTRIES = mapOf(
            // https://www.xe.com/ibancalculator/sample/?ibancountry=austria
            "AT" to Country(61, true),
            // https://www.xe.com/ibancalculator/sample/?ibancountry=germany
            "DE" to Country(89, true)
        )
    }
}
