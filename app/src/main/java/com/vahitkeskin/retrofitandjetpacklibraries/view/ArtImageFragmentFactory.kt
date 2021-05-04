package com.vahitkeskin.retrofitandjetpacklibraries.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.vahitkeskin.retrofitandjetpacklibraries.adapter.ArtImageAdapter
import com.vahitkeskin.retrofitandjetpacklibraries.adapter.ArtRowAdapter
import javax.inject.Inject

class ArtImageFragmentFactory @Inject constructor(
    private val artRowAdapter: ArtRowAdapter,
    private val glide: RequestManager,
    private val artImageAdapter: ArtImageAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            FeedFragment::class.java.name -> FeedFragment(artRowAdapter)
            DetailsFragment::class.java.name -> DetailsFragment(glide)
            SearchFragment::class.java.name -> SearchFragment(artImageAdapter)
            else -> return super.instantiate(classLoader, className)
        }

    }
}