package com.app.func.login_demo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.MainContainFragmentBinding
import com.app.func.utils.Constants
import com.app.func.utils.Logger
import java.util.Date

class MainContainFragment : BaseFragment<MainContainFragmentBinding>() {

    private val longTitle1 = "My notification"
    private val longTitle2 = "My notification channel 2"
    private val longText1 =
        "Much longer text that cannot fit one line. I think it need more, at least is two lines."
    private val longText2 =
        "Much longer text that cannot fit one line of channel 2. I think it need more, at least is two lines. Much longer text that cannot fit one line of channel 2. I think it need more, at least is two lines."

    override fun getViewBinding() = MainContainFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initActions() {
        binding?.btnHome?.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding?.btnSignIn?.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }
        binding?.btnSignUp?.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        binding?.btnGotoRecyclerView?.setOnClickListener {
            findNavController().navigate(R.id.listUserFragment)
        }

        binding?.btnProfile?.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        binding?.viewPager?.setOnClickListener {
            findNavController().navigate(R.id.viewPagerFragment)
        }
        binding?.btnRoomWithRx?.setOnClickListener {
            findNavController().navigate(R.id.noteHomeFragment)
        }
        binding?.btnRoomCoroutines?.setOnClickListener {
            findNavController().navigate(R.id.mainRoomCoroutinesFragment)
        }
        binding?.btnEditTexts?.setOnClickListener {
            findNavController().navigate(R.id.imageGalleryFragment)
        }
        binding?.btnNotify?.setOnClickListener {
            showNotification()
        }
        binding?.btnNotify2?.setOnClickListener {
            showNotification2()
        }
        binding?.btnCoroutinesFunc?.setOnClickListener {
            findNavController().navigate(R.id.snowyMainFragment)
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotification() {
        val notification: Notification =
            NotificationCompat.Builder(requireContext(), Constants.CHANNEL_NOTIFY_ID)
                .setSmallIcon(R.drawable.her)
                .setContentTitle(longTitle1)
                .setContentText(longText1)
                .setStyle(NotificationCompat.BigTextStyle().bigText(longText1))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        if (isPermitPostNotification()) {
            NotificationManagerCompat.from(requireContext())
                .notify(getNotificationId(), notification)
        } else {
            Logger.d("POST_NOTIFICATIONS is denied.")
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotification2() {
        val notification: Notification =
            NotificationCompat.Builder(requireContext(), Constants.CHANNEL_NOTIFY_ID_2)
                .setSmallIcon(R.drawable.her)
                .setContentTitle(longTitle2)
                .setContentText(longText2)
                .setStyle(NotificationCompat.BigTextStyle().bigText(longText2))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        if (isPermitPostNotification()) {
            NotificationManagerCompat.from(requireContext())
                .notify(getNotificationId(), notification)
        } else {
            Logger.d("POST_NOTIFICATIONS is denied.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isPermitPostNotification(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getNotificationId(): Int {
        val time = Date().time.toInt()
        Logger.d("$time")
        return time
    }

}