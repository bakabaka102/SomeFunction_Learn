package hn.single.server.ui.waveline

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentWaveLineBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WaveLineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WaveLineFragment : BaseFragment<FragmentWaveLineBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getViewBinding() = FragmentWaveLineBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val args = WaveLineFragmentArgs.fromBundle(requireArguments())
        val nameTitle = args.nameTitle // từ Safe Args
        val movie = args.movie // từ Safe Args
        val movieList = args.movieList // từ Safe Args
        val setting = args.settingBundle // từ Safe Args
        val isTemp = arguments?.getBoolean("isTemp", false) // từ Bundle thêm
        val bgColor = arguments?.getInt("bgColor", Color.WHITE)

        val result = "nameTitle: $nameTitle --- movie: $movie --- movieList: $movieList --- setting: $setting --- isTemp: $isTemp --- bgColor: $bgColor"
        binding.textView.text = result
    }

    override fun isBottomNavVisible(): Boolean = false

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WaveLineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WaveLineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}