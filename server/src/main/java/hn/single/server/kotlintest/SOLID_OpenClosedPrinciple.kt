package hn.single.server.kotlintest

import kotlin.math.max

interface ICustomerProfile {

    fun isLoyalCustomer(): Boolean
}

class HealthInsuranceCustomerProfile() : ICustomerProfile {
    override fun isLoyalCustomer(): Boolean {
        return true
    }
}

class VehicleInsuranceCustomerProfile() : ICustomerProfile {
    override fun isLoyalCustomer(): Boolean {
        return false
    }
}

class HomeInsuranceCustomerProfile() : ICustomerProfile {
    override fun isLoyalCustomer(): Boolean {
        return true
    }
}

class InsuranceDiscountCalculator() {

    fun calculateDiscountPercent(profile: ICustomerProfile): Double {
        return if (profile.isLoyalCustomer()) {
            15.0
        } else 0.0
    }
}

fun main() {
    val insuranceDiscountCalculator = InsuranceDiscountCalculator()
    val healthInsuranceCustomerProfile = HealthInsuranceCustomerProfile()
    println("Discount for you: ${insuranceDiscountCalculator.calculateDiscountPercent(healthInsuranceCustomerProfile)}%")
}