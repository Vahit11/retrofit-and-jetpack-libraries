package com.vahitkeskin.retrofitandjetpacklibraries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.adapter.ArtImageAdapter
import com.vahitkeskin.retrofitandjetpacklibraries.adapter.ArtRowAdapter
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentFeedBinding
import com.vahitkeskin.retrofitandjetpacklibraries.viewmodel.ArtImagesViewModel
import javax.inject.Inject

class FeedFragment @Inject constructor(
    private val artRowAdapter: ArtRowAdapter
): Fragment(R.layout.fragment_feed) {

    private var fragmentFeedBinding: FragmentFeedBinding? = null
    lateinit var viewModel: ArtImagesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtImagesViewModel::class.java)

        val binding = FragmentFeedBinding.bind(view)
        fragmentFeedBinding = binding

        fragmentFeedBinding = FragmentFeedBinding.bind(view)

        subscribeToObservers()

        binding.recyclerViewFeed.adapter = artRowAdapter
        binding.recyclerViewFeed.layoutManager = LinearLayoutManager(requireContext())


        binding.fab.setOnClickListener {
            findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artImageList.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onDestroy() {
        fragmentFeedBinding = null
        super.onDestroy()
    }
}