package com.vahitkeskin.retrofitandjetpacklibraries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.databinding.FragmentFeedBinding

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFeedBinding.bind(view)
        binding.fab.setOnClickListener {
            Thread.sleep(1000)
            Toast.makeText(context, "Okey yess", Toast.LENGTH_LONG).show()
        }
    }
}