package hn.single.server.kotlintest


open class Product() {
    var name: String = "Product A"
    var price: Double = 10.0
    var discount: Double = 10.0

    open fun discountValue(): Double {
        return discount
    }

    override fun toString(): String {
        return "Product(name='$name', price=$price, discount=$discount)"
    }
}

class InHouseProduct() : Product() {

    override fun discountValue(): Double {
        this.applyDiscount()
        return discount
    }

    fun applyDiscount() {
        discount *= 2
    }
}

open class Vehicle() {

    private val defaultInterior = 100.0

    open fun getInteriorWidth(): Double {
        return defaultInterior
    }
}

class Car() : Vehicle() {
    override fun getInteriorWidth(): Double {
        return getCabinWidth()
    }

    fun getCabinWidth(): Double {
        return 120.0
    }
}

class RacingCar() : Vehicle() {
    override fun getInteriorWidth(): Double {
        return getCockPitWidth()
    }

    private fun getCockPitWidth(): Double {
        return 80.0
    }
}

fun main() {
    val product = Product()
    val inHouseProduct = InHouseProduct()
    val product2 = Product()
    listOf(product, product2, inHouseProduct).forEach {
        println("This product is $it discount: ${it.discountValue()}")
    }

    listOf(Vehicle(), Car(), RacingCar()).forEach {
        println("The $it has interior width = ${it.getInteriorWidth()}")
    }
}