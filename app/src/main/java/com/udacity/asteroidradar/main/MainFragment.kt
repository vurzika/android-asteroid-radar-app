package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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

        viewModel.refreshPictureOfDay()

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option_show_today -> viewModel.setAsteroidsToDisplay(MainViewModel.AsteroidsToDisplay.TODAY)
            R.id.menu_option_show_weekly -> viewModel.setAsteroidsToDisplay(MainViewModel.AsteroidsToDisplay.WEEKLY)
            R.id.menu_option_show_saved -> viewModel.setAsteroidsToDisplay(MainViewModel.AsteroidsToDisplay.SAVED)

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}
