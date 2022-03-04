package com.example.justbeer.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.justbeer.R
import com.example.justbeer.databinding.FragmentDetailBeerBinding
import com.example.justbeer.model.BeerResultsItem

class DetailBeerFragment : Fragment() {

    private lateinit var binding: FragmentDetailBeerBinding
    private lateinit var beer: BeerResultsItem

    companion object {
        private const val EXTRA_BEER = "extra_beer"

        @JvmStatic
        fun newInstance(beer: BeerResultsItem): DetailBeerFragment {
            return DetailBeerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_BEER, beer)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.beer_detail)
        binding = FragmentDetailBeerBinding.inflate(inflater, container, false)

        arguments.let {
            beer = it?.getSerializable(EXTRA_BEER) as BeerResultsItem
            bindView(beer)
        }

        return binding.root
    }

    private fun bindView(beer: BeerResultsItem) {
        binding.beerNameApiDetail.text = beer.name
        binding.taglineBeerDetail.text = beer.tagline
        binding.abvDetailApi.text = String.format("%s%%", beer.abv)
        binding.calendarDetailApi.text = beer.first_brewed
        binding.beerDescriptionDetail.text = beer.description
        Glide
            .with(binding.root.context)
            .load(beer.image_url)
            .into(binding.beerThumbnailDetail)
    }

}