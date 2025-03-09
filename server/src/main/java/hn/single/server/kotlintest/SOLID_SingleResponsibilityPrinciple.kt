package hn.single.server.kotlintest

/*data class Book(val name: String, val price: Double, val publishDate: String) {

    val list = mutableListOf<Book>()
    val maxSize = 10

    fun addBook(book: Book) {
        if (list.size < maxSize) {
            list.add(book)
        }
    }

    fun save(book: Book) {
        println("Save book ${book.name}")
    }

}*/

data class Book(var name: String = "BookBook", var price: Double = 10.0, var publishDate: String = "01/01/1999")


class BookUtil() {

    val list = mutableListOf<Book>()
    val maxSize = 10

    fun addBook(book: Book) {
        if (list.size < maxSize) {
            println("Add book ${book.name}")
            list.add(book)
        } else {
            println("Book list is full")
        }
    }

    fun save(book: Book) {
        println("Save book ${book.name}")
    }

    fun sortBook(book1: Book, book2: Book) {
    }
}

fun main() {
    /*val book = Book("BookBook", 10.0, "01/01/1999")
    book.save(book)*/
    //use
    val bookUtil = BookUtil()
    val book = Book("BookBook", 10.0, "01/01/1999")
    bookUtil.addBook(book)
    bookUtil.save(book)

}