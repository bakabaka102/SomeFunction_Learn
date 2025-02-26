package hn.single.server.aidlservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.hn.libs.ICalculatorAidl

class CalculatorAidl : Service() {

    private val binder: ICalculatorAidl.Stub = object : ICalculatorAidl.Stub() {
        override fun add(a: Int, b: Int): Int {
            if (a / 2 + b / 2 >= Int.MAX_VALUE / 2) {
                throw IllegalArgumentException("Out of range")
            } else {
                return a + b
            }
        }

        override fun subtract(a: Int, b: Int): Int {
            if (a / 2 - b / 2 <= Int.MIN_VALUE / 2) {
                throw IllegalArgumentException("Out of range")
            } else {
                return a - b
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

}