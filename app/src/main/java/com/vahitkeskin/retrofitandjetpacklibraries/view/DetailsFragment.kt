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
import kotlinx.android.synthetic.*
import javax.inject.Inject

class DetailsFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_details) {

    private var binding: FragmentDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)
        binding?.civArtDetails?.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToSearchFragment())
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}