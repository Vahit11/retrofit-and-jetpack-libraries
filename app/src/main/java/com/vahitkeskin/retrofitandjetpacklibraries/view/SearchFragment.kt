package com.vahitkeskin.retrofitandjetpacklibraries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.adapter.ArtImageAdapter
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentDetailsBinding
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentSearchBinding
import com.vahitkeskin.retrofitandjetpacklibraries.util.Status
import com.vahitkeskin.retrofitandjetpacklibraries.viewmodel.ArtImagesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment @Inject constructor(
    val artImageAdapter: ArtImageAdapter
): Fragment(R.layout.fragment_search) {

    private var fragmentSearchBinding: FragmentSearchBinding? = null
    lateinit var viewModel: ArtImagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtImagesViewModel::class.java)

        val binding = FragmentSearchBinding.bind(view)
        fragmentSearchBinding = binding

        var job : Job? = null

        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeObservers()

        binding.rvSearchImage.adapter = artImageAdapter
        binding.rvSearchImage.layoutManager = GridLayoutManager(requireContext(),3)
        artImageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
            println("it: $it")
        }
    }

    private fun subscribeObservers() {
        viewModel.imagesList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {imageResult ->
                        imageResult.previewURL
                    }
                    artImageAdapter.images = urls ?: listOf()
                    fragmentSearchBinding?.pbLoadImage?.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                    fragmentSearchBinding?.pbLoadImage?.visibility = View.GONE
                }

                Status.LOADING -> {
                    fragmentSearchBinding?.pbLoadImage?.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroy() {
        fragmentSearchBinding = null
        super.onDestroy()
    }
}