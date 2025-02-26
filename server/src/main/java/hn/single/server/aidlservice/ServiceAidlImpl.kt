package hn.single.server.aidlservice

import android.util.Log
import hn.single.server.IServiceAidl

class ServiceAidlImpl : IServiceAidl.Stub() {

    override fun sayHi() {
        Log.i("AIDL-service", "AIDL-server call sayHi")
    }

    override fun sum(a: Int, b: Int): Int {
        return a + b
    }
}