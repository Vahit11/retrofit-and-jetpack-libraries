package com.vahitkeskin.retrofitandjetpacklibraries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.adapter.ArtRowAdapter
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentFeedBinding
import com.vahitkeskin.retrofitandjetpacklibraries.viewmodel.ArtImagesViewModel
import javax.inject.Inject

class FeedFragment @Inject constructor(
    private val artRowAdapter: ArtRowAdapter
) : Fragment(R.layout.fragment_feed) {

    private var fragmentFeedBinding: FragmentFeedBinding? = null
    private lateinit var viewModel: ArtImagesViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectItemArtRow = artRowAdapter.artsDB[layoutPosition]
            fragmentFeedBinding?.let { ffb ->
                Snackbar.make(
                    ffb.rootLayout,
                    "${selectItemArtRow.savedUserName} - ${selectItemArtRow.imageName} Wiping out...",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            viewModel.deleteArtRoom(selectItemArtRow)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtImagesViewModel::class.java)

        val binding = FragmentFeedBinding.bind(view)
        fragmentFeedBinding = binding

        fragmentFeedBinding = FragmentFeedBinding.bind(view)

        subscribeToObservers()

        binding.recyclerViewFeed.adapter = artRowAdapter
        binding.recyclerViewFeed.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewFeed)

        binding.fab.setOnClickListener {
            findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artImageList.observe(viewLifecycleOwner, {
            artRowAdapter.artsDB = it
        })
    }

    override fun onDestroy() {
        fragmentFeedBinding = null
        super.onDestroy()
    }
}