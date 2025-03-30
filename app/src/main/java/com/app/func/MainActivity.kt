package com.app.func

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityMainBinding
import com.hn.libs.ICalculatorAidl
import hn.single.server.IServiceAidl

class MainActivity : BaseActivity<ActivityMainBinding>()
/*,
NavigationView.OnNavigationItemSelectedListener*/ {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    private val mOnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }
    }
    private var service: IServiceAidl? = null
    private var calculatorService: ICalculatorAidl? = null

    companion object {
        const val ACTION = "hn.single.server.IServiceAidl"
        const val ACTION_CAL = "com.hn.libs.ICalculatorAidl"
        const val PKG_SERVER = "hn.single.server"
    }

    override fun instanceViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = IServiceAidl.Stub.asInterface(binder)
            Log.i("AIDL-Client", "Service connected: sum = ${service?.sum(1, 2)}")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
            Log.i("AIDL-Client", "Service disconnected")
        }

    }

    private val calculatorServiceConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            calculatorService = ICalculatorAidl.Stub.asInterface(service)
            Log.i("AIDL-Client", "CalculatorService connected: sum = ${calculatorService?.add(11, 22)}")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            calculatorService = null
            Log.i("AIDL-Client", "CalculatorService disconnected")
        }
    }

    override fun initViews() {
        initDrawerLayout()
        service?.sayHi()
    }

    private fun bindAidl() {
        // Bind to the AIDL Service
        val intent = Intent(ACTION).apply {
            setPackage(PKG_SERVER)
        }

        try {
            val intentCal = Intent().apply {
                action = ACTION_CAL
                `package` = PKG_SERVER
            }
            bindService(intentCal, calculatorServiceConn, BIND_AUTO_CREATE).also {
                Log.i("AIDL-Client", "CalculatorAidl bindService is $it")
            }
            bindService(intent, serviceConnection, BIND_AUTO_CREATE).also {
                Log.i("AIDL-Client", "bindService is $it")
            }
        } catch (e: Exception) {
            Log.e("AIDL-Client", "bindService exception ${e.message}")
        }
    }

    override fun onStart() {
        super.onStart()
        bindAidl()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    override fun initActions() {
        /*mBinding.mainLeftContent.leftMainContentAbout.setOnClickListener {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show()
        }
        mBinding.mainLeftContent.leftMainContentHelp.setOnClickListener {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show()
        }*/
        onBackPressedDispatcher.addCallback(mOnBackPressedCallback)
//mBinding.mainNavigationView.setNavigationItemSelectedListener(this)
    }

    private fun initDrawerLayout() {
// drawer layout instance to toggle the menu icon to open drawer and back button to close drawer
        mDrawerLayout = mBinding.rootDrawerLayout
        mActionBarDrawerToggle =
            ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_open, R.string.nav_close)
// pass the Open and Close toggle for the drawer layout listener to toggle the button
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.syncState()
// to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
// Click button home open/close
//supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_account -> {
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_settings -> {
                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_logout -> {
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                true
                //super.onOptionsItemSelected(item)
            }
        }
    }

//If use layout by Menu default, others need not it.
    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.nav_account -> {
            Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.nav_settings -> {
            Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.nav_logout -> {
            Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            true
        }
    }
    }*/
}