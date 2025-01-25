package hn.single.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    // AIDL implementation
    private val binder: IServiceAidl.Stub = object : IServiceAidl.Stub() {
        override fun sayHi() {
            Log.i("AIDL-Service", "sayHi() called from client")
        }

        override fun sum(a: Int, b: Int): Int {
            return a + b
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        // Return the Binder to the client
        return binder
    }

}