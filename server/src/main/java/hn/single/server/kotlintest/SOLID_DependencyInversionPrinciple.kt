package hn.single.server.kotlintest


/*
class SQLProductRepository() {

    fun getProducts(): List<Product> {
        return listOf(Product(), Product())
    }
}

class ProductCategory() {

    val productRepository = SQLProductRepository()

    fun getProducts(): List<Product> {
        return productRepository.getProducts()
    }
}
*/

interface IProductRepository {

    fun getProducts(): List<Product>

}

class SQLProductRepository : IProductRepository {

    override fun getProducts(): List<Product> {
        return listOf(Product(), Product())
    }
}

class ProductFactory {

    fun create(): IProductRepository {
        return SQLProductRepository()
    }
}

class ProductCategory() {

    fun showAllProduct() {
        val repository = ProductFactory().create()
        repository.getProducts().forEach {
            println("Product: $it")
        }
    }
}

// Interface (abstraction)
interface IRepository {
    fun getData(): String
}

// Implementation of IRepository
class Repository : IRepository {
    override fun getData(): String {
        return "Data from Repository"
    }
}

// High-level module
class Service(private val repository: IRepository) {

    fun fetchData(): String {
        return repository.getData()
    }
}

// Usage
fun main() {
    val productCategory = ProductCategory()
    productCategory.showAllProduct()
    val repository: IRepository = Repository()
    val service = Service(repository)
    println(service.fetchData())
}