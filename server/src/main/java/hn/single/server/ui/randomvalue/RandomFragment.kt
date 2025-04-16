package hn.single.server.ui.randomvalue

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import hn.single.server.R
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentRandomBinding
import kotlinx.coroutines.launch

private const val ARG_RANDOM = "ARG_RANDOM"

/**
 * A simple [Fragment] subclass.
 * Use the [RandomFragment.newInstance] factory method to
 * create an instance of this fragment.
 * MVI: https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d or https://archive.is/D2geF
 */
class RandomFragment : BaseFragment<FragmentRandomBinding>() {
    private var param1: String? = null
    private val viewModel: RandomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_RANDOM)
        }
    }

    override fun getViewBinding() = FragmentRandomBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.randomNumberState) {
                    RandomContract.RandomNumberState.Idle -> {
                        binding?.progressBar?.isVisible = false
                    }

                    RandomContract.RandomNumberState.Loading -> {
                        binding?.progressBar?.isVisible = true
                    }

                    is RandomContract.RandomNumberState.Success -> {
                        val number = it.randomNumberState.number.toString()
                        binding?.progressBar?.isVisible = false
                        binding?.number?.text = getString(R.string.success_data, number)
                    }
                }
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is RandomContract.Effect.ShowToast -> {
                            binding?.progressBar?.isVisible = false
                            binding?.number?.text = "Hello World! - No data"
                            Log.i("RandomFragment", "Error, number is even")
                            showToast("Error, number is even")
                        }
                    }
                }
            }
        }
    }

    override fun observeView() {

    }

    override fun initActions() {
        binding?.generateNumber?.setOnClickListener {
            viewModel.setEvent(RandomContract.Event.OnRandomNumberClicked)
        }
        binding?.showToast?.setOnClickListener {
            viewModel.setEvent(RandomContract.Event.OnShowToastClicked)
            findNavController().navigate(R.id.bombExplosionFragment)
        }
        binding?.secondActivity?.setOnClickListener {
            findNavController().navigate(R.id.action_randomFragment_to_mainMovieFragment)
        }
        binding?.waveLine?.setOnClickListener {
            val configMap = mapOf(
                "theme" to "dark",
                "username" to "admin",
                "language" to "vi"
            )
            val settings = SettingsBundle(configMap)
            val action = RandomFragmentDirections
                .actionRandomFragmentToWaveLineFragment(
                    nameTitle = "This is Safe Args Title",
                    movie = Movie(
                        id = 1,
                        title = "This is Safe Args Movie",
                        isFavorite = true
                    ),
                    movieList = arrayOf(
                        Movie(
                            id = 1,
                            title = "This is Safe Args Movie",
                            isFavorite = true
                        ), Movie(
                            id = 2,
                            title = "This is Safe Args Movie 2",
                            isFavorite = false
                        )
                    ),
                    settingBundle = settings,
                )

            val bundle = Bundle().apply {
                putBoolean("isTemp", true)
                putInt("bgColor", Color.RED)
            }

            //Simple
            //findNavController().navigate(action)
            // Gộp SafeArgs + Bundle
            findNavController().navigate(action.actionId, action.arguments.apply {
                putAll(bundle) // gộp bundle phụ vào bundle chính của SafeArgs
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(data: String? = null) = RandomFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_RANDOM, data)
            }
        }
    }
}