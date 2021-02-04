package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        // Adapter
        val adapter = MainListAdapter()
        adapter.onItemClickListener = MainListAdapter.ClickListener { asteroid ->
            findNavController().navigate(
                MainFragmentDirections.actionShowDetail(asteroid)
            )
        }
        binding.asteroidList.adapter = adapter

        // Data
        viewModel.asteroidList.observe(viewLifecycleOwner, { asteroidList ->
            adapter.submitList(asteroidList)

            adapter.notifyDataSetChanged()
        })

        viewModel.refreshAsteroidData()

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
