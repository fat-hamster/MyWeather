package com.dmgpersonal.myweatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dmgpersonal.myweatherapp.R
import com.dmgpersonal.myweatherapp.databinding.FragmentDetailsBinding
import com.dmgpersonal.myweatherapp.model.Weather
import com.dmgpersonal.myweatherapp.model.WeatherDTO
import com.dmgpersonal.myweatherapp.model.WeatherDTOOpenWeather

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherBundle : Weather

    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                displayWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                Toast.makeText(context, "Error %(", Toast.LENGTH_LONG).show()
            }

        }

//    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
//        object : WeatherLoader.WeatherLoaderListener {
//            override fun onLoaded(weatherDTO: WeatherDTOOpenWeather) {
//                displayWeather(weatherDTO)
//            }
//
//            override fun onFailed(throwable: Throwable) {
//                Toast.makeText(context, "Error %(", Toast.LENGTH_LONG).show()
//            }
//
//        }

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

        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()

        with(binding) {
            mainView.visibility = View.GONE
            loadingLayout.visibility = View.VISIBLE
        }

        val loader = WeatherLoader(onLoadListener, weatherBundle.city.lat, weatherBundle.city.lon)
        loader.loadWeather()
    }

    private fun displayWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE

            weatherBundle.city.also { city ->
                cityName.text = city.city
                cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
                )
                temperatureValue.text = weatherDTO.fact?.temp.toString()
                feelsLikeValue.text = weatherDTO.fact?.feels_like.toString()
                conditionValue.text = weatherDTO.fact?.condition.toString()
            }
        }
    }

//    private fun displayWeather(weatherDTO: WeatherDTOOpenWeather) {
//        with(binding) {
//            mainView.visibility = View.VISIBLE
//            loadingLayout.visibility = View.GONE
//
//            weatherBundle.city.also { city ->
//                cityName.text = city.city
//                cityCoordinates.text = String.format(
//                    getString(R.string.city_coordinates),
//                    city.lat.toString(),
//                    city.lon.toString()
//                )
//                temperatureValue.text = weatherDTO.fact?.temp.toString()
//                feelsLikeValue.text = weatherDTO.fact?.feels_like.toString()
//                conditionValue.text = weatherDTO.fact?.condition.toString()
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}