package com.dmgpersonal.myweatherapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.dmgpersonal.myweatherapp.viewmodel.AppState
import com.dmgpersonal.myweatherapp.R
import com.dmgpersonal.myweatherapp.databinding.FragmentDetailsBinding
import com.dmgpersonal.myweatherapp.model.Weather
import com.dmgpersonal.myweatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle) : DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let {
            weather ->
            weather.city.also { city ->
                binding.cityName.text = city.city
                binding.cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString())
                binding.temperatureValue.text = weather.temperature.toString()
                binding.feelsLikeValue.text = weather.feelsLike.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}