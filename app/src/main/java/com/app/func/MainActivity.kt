package com.app.func

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Resources
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityMainBinding
import com.app.func.features.scheduler.AlarmReceiver
import com.app.func.utils.Logger
import com.hn.libs.ICalculatorAidl
import hn.single.server.IServiceAidl
import java.util.Calendar

class MainActivity : BaseActivity<ActivityMainBinding>()
/*,
NavigationView.OnNavigationItemSelectedListener*/ {

    private lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    private val mOnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (mBinding.root.isDrawerOpen(GravityCompat.START)) {
                mBinding.root.closeDrawer(GravityCompat.START)
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
            Log.i(
                "AIDL-Client",
                "CalculatorService connected: sum = ${calculatorService?.add(11, 22)}"
            )
        }

        override fun onServiceDisconnected(name: ComponentName) {
            calculatorService = null
            Log.i("AIDL-Client", "CalculatorService disconnected")
        }
    }

    override fun initViews() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //FloatingActionButton, near right/bottom corner
        /*ViewCompat.setOnApplyWindowInsetsListener(mBinding.fab) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            // Return CONSUMED if you don't want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }*/

        ViewCompat.setOnApplyWindowInsetsListener(mBinding.fab) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val lp = view.layoutParams as ViewGroup.MarginLayoutParams
            lp.bottomMargin = systemBars.bottom + 16.dp
            lp.marginEnd = systemBars.right + 16.dp
            view.layoutParams = lp
            insets
        }
        initDrawerLayout()
        service?.sayHi()
        scheduleDailyAlarm()
    }

    val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()


    private fun scheduleDailyAlarm() {
        //val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        //val alarmManager: AlarmManager? = ContextCompat.getSystemService(this, AlarmManager::class.java)
        val alarmManager: AlarmManager? = getSystemService(AlarmManager::class.java)
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = calculateTimeScheduler()
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent,
        )
        Logger.d("AlarmScheduler - Alarm scheduled for: ${calendar.time}")
    }

    private fun calculateTimeScheduler(): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 45)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar
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
        onBackPressedDispatcher.addCallback(mOnBackPressedCallback)
    }

    private fun initDrawerLayout() {
        // drawer layout instance to toggle the menu icon to open drawer and back button to close drawer
        mActionBarDrawerToggle =
            ActionBarDrawerToggle(this, mBinding.root, R.string.nav_open, R.string.nav_close)
        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        mBinding.root.addDrawerListener(mActionBarDrawerToggle)
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
}