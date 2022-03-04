package com.example.justbeer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justbeer.databinding.ItemListBinding
import com.example.justbeer.model.BeerResultsItem

class BeerAdapter(val clickListener: ItemClickListener) :
    RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    private val beers: ArrayList<BeerResultsItem> = arrayListOf()

    fun setBeers(newBeers: List<BeerResultsItem>) {
        beers.clear()
        beers.addAll(newBeers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(beers[position])
    }

    override fun getItemCount(): Int = beers.size

    inner class BeerViewHolder(private val binding: ItemListBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(beer: BeerResultsItem) {
            binding.beerName.text = beer.name
            binding.beerTagline.text = beer.tagline
            binding.beerAbv.text = beer.abv.toString()
                Glide
                    .with(binding.root.context)
                    .load(beer.image_url)
                    .into(binding.beerThumbnail)
            binding.root.setOnClickListener {
                clickListener.onClick(beer)
            }
        }
    }

    interface ItemClickListener {
        fun onClick(item: BeerResultsItem)
    }

}