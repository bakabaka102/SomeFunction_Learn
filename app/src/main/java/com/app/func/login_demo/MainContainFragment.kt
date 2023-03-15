package com.app.func.login_demo

import android.app.Notification
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.MainContainFragmentBinding
import com.app.func.utils.Constants
import com.app.func.utils.Logger
import java.util.*

class MainContainFragment : BaseFragment() {

    private var binding: MainContainFragmentBinding? = null
    private val viewModel: LoginViewModel by viewModels()
    private val longTitle1 = "My notification"
    private val longTitle2 = "My notification channel 2"
    private val longText1 =
        "Much longer text that cannot fit one line. I think it need more, at least is two lines."
    private val longText2 =
        "Much longer text that cannot fit one line of channel 2. I think it need more, at least is two lines. Much longer text that cannot fit one line of channel 2. I think it need more, at least is two lines."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = MainContainFragmentBinding.inflate(inflater, container, false)

        initActions()
        return binding?.root
    }

    private fun initActions() {
        binding?.btnHome?.setOnClickListener {
            getNavController()?.navigate(R.id.homeFragment)
        }
        binding?.btnSignIn?.setOnClickListener {
            getNavController()?.navigate(R.id.signInFragment)
        }
        binding?.btnSignUp?.setOnClickListener {
            getNavController()?.navigate(R.id.signUpFragment)
        }
        binding?.btnGotoRecyclerView?.setOnClickListener {
            getNavController()?.navigate(R.id.listUserFragment)
        }

        binding?.btnProfile?.setOnClickListener {
            getNavController()?.navigate(R.id.profileFragment)
        }
        binding?.viewPager?.setOnClickListener {
            getNavController()?.navigate(R.id.viewPagerFragment)
        }
        binding?.btnRoomWithRx?.setOnClickListener {
            getNavController()?.navigate(R.id.noteHomeFragment)
        }
        binding?.btnRoomCoroutines?.setOnClickListener {
            getNavController()?.navigate(R.id.mainRoomCoroutinesFragment)
        }
        binding?.btnEditTexts?.setOnClickListener {
            getNavController()?.navigate(R.id.imageGalleryFragment)
        }
        binding?.btnNotify?.setOnClickListener {
            showNotification()
        }
        binding?.btnNotify2?.setOnClickListener {
            showNotification2()
        }
    }

    private fun showNotification() {
        val notification: Notification =
            NotificationCompat.Builder(requireContext(), Constants.CHANNEL_NOTIFY_ID)
                .setSmallIcon(R.drawable.her)
                .setContentTitle(longTitle1)
                .setContentText(longText1)
                .setStyle(NotificationCompat.BigTextStyle().bigText(longText1))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        NotificationManagerCompat.from(requireContext()).notify(getNotificationId(), notification)
    }

    private fun showNotification2() {
        val notification: Notification =
            NotificationCompat.Builder(requireContext(), Constants.CHANNEL_NOTIFY_ID_2)
                .setSmallIcon(R.drawable.her)
                .setContentTitle(longTitle2)
                .setContentText(longText2)
                .setStyle(NotificationCompat.BigTextStyle().bigText(longText2))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        NotificationManagerCompat.from(requireContext()).notify(getNotificationId(), notification)
    }

    private fun getNotificationId(): Int {
        val time = Date().time.toInt()
        Logger.d("$time")
        return time
    }

    companion object {
        fun newInstance() = MainContainFragment()
    }


}