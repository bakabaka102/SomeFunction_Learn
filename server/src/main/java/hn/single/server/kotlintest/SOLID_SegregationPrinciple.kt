package hn.single.server.kotlintest


/*
interface IMultiFunctionDevice {

    fun print()

    fun scan()

    fun fax()
}

class XeroxWorkCenter : IMultiFunctionDevice {

    override fun print() {
        println("Print")
    }

    override fun scan() {
        println("Scan")
    }

    override fun fax() {
        println("Fax")
    }
}

class CanonPrinter : IMultiFunctionDevice {

    override fun print() {
        println("Print")
    }

    override fun scan() {
        println("Scan")
    }

    override fun fax() {
        throw UnsupportedOperationException("Fax not supported")
    }
}

class HPScanner : IMultiFunctionDevice {

    override fun print() {
        throw UnsupportedOperationException("Print not supported")
    }

    override fun scan() {
        println("Scan")
    }

    override fun fax() {
        throw UnsupportedOperationException("Fax not supported")
    }
}*/

//Should
interface IPrinter {

    fun print()
}

interface ISCaner {

    fun scan()
}

interface IFax {

    fun fax()
}

class XeroxWorkCenter : IPrinter, ISCaner, IFax {

    override fun print() {
        println("XeroxWorkCenter print")
    }

    override fun scan() {
        println("XeroxWorkCenter scan")
    }

    override fun fax() {
        println("XeroxWorkCenter fax")
    }
}

class HPScanner : ISCaner {

    override fun scan() {
        println("HPScaner scan")
    }
}

class CanonPrinter : IPrinter, ISCaner {

    override fun print() {
        println("CannonPrinter print")
    }

    override fun scan() {
        println("CanonPrinter scan")
    }
}