package hn.single.server.kotlintest


internal interface PaymentStrategy {

    fun pay(amount: Float)

}

internal class CreditCardStrategy(
    private val name: String,
    private val cardNumber: String,
    private val cvv: String,
    private val dateOfExpiry: String
) : PaymentStrategy {

    override fun pay(amount: Float) {
        println("$amount paid with credit/debit card")
    }

}

internal class PayPalStrategy(private val emailId: String, private val password: String) : PaymentStrategy {
    override fun pay(amount: Float) {
        println("$amount paid using PayPal.")
    }
}

internal class ShoppingCart {
    private var items: MutableList<Item> = ArrayList<Item>()
    private var paymentStrategy: PaymentStrategy? = null

    fun addItem(item: Item) {
        items.add(item)
    }

    fun removeItem(item: Item) {
        items.remove(item)
    }

    fun calculateTotal(): Float {
        var sum = 0f
        for (item in items) {
            sum += item.price
        }
        return sum
    }

    fun pay() {
        val amount = calculateTotal()
        paymentStrategy?.pay(amount)
    }

    fun setPaymentStrategy(paymentStrategy: PaymentStrategy?) {
        this.paymentStrategy = paymentStrategy
    }
}

data class Item(val price: Float)

fun main() {
    val cart = ShoppingCart()
    cart.addItem(Item(100f))
    cart.addItem(Item(200f))
    cart.addItem(Item(300f))
    cart.pay()
    cart.setPaymentStrategy(CreditCardStrategy("Pankaj Kumar", "1234567890123456", "786", "12/15"))
    cart.pay()
    cart.setPaymentStrategy(PayPalStrategy("123", "123"))
}