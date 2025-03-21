package com.app.func.login_demo

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.MainContainFragmentBinding
import com.app.func.notification.NotificationBuilder
import com.app.func.utils.Logger
import com.app.func.utils.MyToast
import java.util.Date

class MainContainFragment : BaseFragment<MainContainFragmentBinding>() {

    private val longTitle1 = "My notification"
    private val longText1 =
        "Much longer text that cannot fit one line. I think it need more, at least is two lines."
    private var notificationBuilder: NotificationBuilder? = null

    override fun getViewBinding() = MainContainFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {
        notificationBuilder = context?.let { NotificationBuilder(it) }
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
        binding?.btnCoroutinesFunc?.setOnClickListener {
            findNavController().navigate(R.id.snowyMainFragment)
        }
    }

    private fun showNotification() {
        MyToast.showToast(context, "Notify", Toast.LENGTH_SHORT)
        notificationBuilder?.createNotificationChannel(
            chanelId = NotificationBuilder.NOTIFICATION_CHANEL_ID,
            channelName = "Notify_1",
            channelDescription = "Notify_${getNotificationId()}",
            importance = NotificationManagerCompat.IMPORTANCE_DEFAULT,
        )
        notificationBuilder?.showNotification(
            chanelId = NotificationBuilder.NOTIFICATION_CHANEL_ID,
            smallIcon = R.mipmap.ic_launcher_round,
            contentTitle = longTitle1,
            contentText = longText1,
            style = NotificationCompat.BigTextStyle().bigText(longText1),
            notifyId = getNotificationId(),
        )
    }

    private fun getNotificationId(): Int {
        val time = Date().time.toInt()
        Logger.d("$time")
        return time
    }

}