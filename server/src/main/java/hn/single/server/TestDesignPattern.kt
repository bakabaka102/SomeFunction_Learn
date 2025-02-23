package hn.single.server

internal interface Subject {

    fun registerObserver(o: Observer?)

    fun removeObserver(o: Observer?)

    fun notifyObservers()
}

internal interface Observer {

    fun update(temp: Float, humidity: Float, pressure: Float)

}

internal class WeatherData : Subject {
    private val observers: MutableList<Observer?> = ArrayList()
    private var temperature = 0f
    private var humidity = 0f
    private var pressure = 0f

    override fun registerObserver(o: Observer?) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer?) {
        val i = observers.indexOf(o)
        if (i >= 0) {
            observers.removeAt(i)
        }
    }

    override fun notifyObservers() {
        for (observer in observers) {
            observer?.update(temperature, humidity, pressure)
        }
    }

    private fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        measurementsChanged()
    }
}

internal class CurrentConditionsDisplay(weatherData: Subject) : Observer {
    private var temperature = 0f
    private var humidity = 0f

    init {
        weatherData.registerObserver(this)
    }

    override fun update(temp: Float, humidity: Float, pressure: Float) {
        this.temperature = temp
        this.humidity = humidity
        display()
    }

    private fun display() {
        println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity")
    }
}

fun main() {
    val weatherData = WeatherData()
    CurrentConditionsDisplay(weatherData)
    weatherData.setMeasurements(80f, 65f, 30.4f)
    weatherData.setMeasurements(82f, 70f, 29.2f)
    weatherData.setMeasurements(78f, 90f, 29.2f)
}