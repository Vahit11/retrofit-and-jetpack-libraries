package com.vahitkeskin.retrofitandjetpacklibraries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentDetailsBinding
import com.vahitkeskin.retrofitandjetpacklibraries.util.Status
import com.vahitkeskin.retrofitandjetpacklibraries.viewmodel.ArtImagesViewModel
import javax.inject.Inject

class DetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_details) {

    private var fragmentDetailsBinding: FragmentDetailsBinding? = null
    private lateinit var viewModel: ArtImagesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtImagesViewModel::class.java)

        val binding = FragmentDetailsBinding.bind(view)
        fragmentDetailsBinding = binding

        subscribeObservers()

        binding.civArtDetails.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToSearchFragment())
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.detailsFab.setOnClickListener {
            viewModel.makeArt(
                binding.detailsImageName.text.toString(),
                binding.detailsSavedUserName.text.toString(),
                binding.detailsSavedTimeName.text.toString(),
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.selectedArtImageUrl.observe(viewLifecycleOwner, Observer {url ->
            fragmentDetailsBinding?.let {
                glide.load(url).into(it.civArtDetails)
            }
        })

        viewModel.insertArtMsg.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    viewModel.resetArtMessage()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroy() {
        fragmentDetailsBinding = null
        super.onDestroy()
    }
}