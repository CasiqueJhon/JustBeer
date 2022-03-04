package com.example.justbeer.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.justbeer.R
import com.example.justbeer.ui.adapter.BeerAdapter
import com.example.justbeer.databinding.FragmentShowBeersBinding
import com.example.justbeer.model.BeerResultsItem
import com.example.justbeer.ui.viewmodel.BeerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowBeersFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModels()

    private lateinit var binding: FragmentShowBeersBinding
    private lateinit var adapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.beer_list_nav_bar_name)

        adapter = BeerAdapter(
            object :
                BeerAdapter.ItemClickListener {
                override fun onClick(item: BeerResultsItem) {
                    showDetailsFragment(item)
                }
            })

        binding = FragmentShowBeersBinding.inflate(inflater, container, false)
        binding.beersRecyclerView.adapter = adapter

        prepareObservers()
        return binding.root

    }

    private fun prepareObservers() {
        viewModel.beers.observe(viewLifecycleOwner) {

            if (it != null) {
                adapter.setBeers(it)
            } else {
                Log.i("showBeersFragment", "Result null")
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.search(s.toString())
            }
        })
    }


private fun showDetailsFragment(beer: BeerResultsItem) {
    parentFragmentManager.beginTransaction()
        .replace(
            R.id.fragmentContainerView,
            DetailBeerFragment.newInstance(beer), "details fragment"
        )
        .addToBackStack(null)
        .commit()
}

}