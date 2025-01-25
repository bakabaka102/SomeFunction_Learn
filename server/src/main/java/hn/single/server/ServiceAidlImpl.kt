package hn.single.server

import android.util.Log

class ServiceAidlImpl : IServiceAidl.Stub() {

    override fun sayHi() {
        Log.i("AIDL-service", "AIDL-server call sayHi")
    }

    override fun sum(a: Int, b: Int): Int {
        return a + b
    }
}