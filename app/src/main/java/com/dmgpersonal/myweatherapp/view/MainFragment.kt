package com.dmgpersonal.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmgpersonal.myweatherapp.R
import com.dmgpersonal.myweatherapp.databinding.FragmentMainBinding
import com.dmgpersonal.myweatherapp.model.Weather
import com.dmgpersonal.myweatherapp.viewmodel.AppState
import com.dmgpersonal.myweatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var isDataSetRus: Boolean = true
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it as AppState) }
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() =
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }.also { isDataSetRus = !isDataSetRus }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { when (isDataSetRus) {
                        true -> viewModel.getWeatherFromLocalSourceRus()
                        false -> viewModel.getWeatherFromLocalSourceWorld() }
                    }
                )
            }
        }
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        lenght: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, lenght).setAction(actionText, action).show()
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}