package com.vahitkeskin.retrofitandjetpacklibraries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentDetailsBinding
import javax.inject.Inject

class DetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_details) {

    private var fragmentDetailsBinding: FragmentDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)
        fragmentDetailsBinding = binding

        binding.civArtDetails.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToSearchFragment())
        }
    }

    override fun onDestroy() {
        fragmentDetailsBinding = null
        super.onDestroy()
    }
}