package com.example.retofoh.ui.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.retofoh.databinding.FragmentHomeBinding
import com.example.retofoh.domain.model.movie
import com.example.retofoh.ui.Adapter.MovieAdapter
import com.example.retofoh.ui.ViewModel.PremierViewModel
import com.example.retofoh.ui.satate.ViewState
import com.example.retofoh.util.configurePagerProperties
import com.example.retofoh.util.constants.BOOLEAN_TRUE_VALUE
import com.example.retofoh.util.constants.CERO_VALUE
import com.example.retofoh.util.constants.ONE_VALUE
import com.example.retofoh.util.handlePageScrollStateChange

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var premierViewModel: PremierViewModel

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var currentPage = CERO_VALUE

    val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(layoutInflater).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        premierViewModel = ViewModelProvider(this).get(PremierViewModel::class.java)
        setObserver()
        setUpView()
    }

    private fun setObserver() {
        premierViewModel.eventState.observe(requireActivity()) { state ->
            when (state) {
                is ViewState.ListMovie ->{
                    initCarrusel(state.listMovie)
                }

                else -> Unit
            }
        }
    }
    private fun setUpView() {
        premierViewModel.getPremier()
    }

    private fun initCarrusel(listCarrusel: List<movie>) {
        if (listCarrusel.size > ONE_VALUE) {
            startAutoScroll()
        }
        setupViewPager(listCarrusel)
    }

    private fun setupViewPager(listmovie: List<movie>) {
        binding?.rvPremiereInclude?.movieViewPager2?.apply {
            adapter = MovieAdapter(listmovie) { item ->

            }
            setCurrentItem(0, false)
            configurePagerProperties(listmovie.size)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentPage = position % listmovie.size
                }

                override fun onPageScrollStateChanged(state: Int) {
                    handlePageScrollStateChange(state)
                }
            })
        }
    }

    private fun startAutoScroll() {
        runnable = object : Runnable {
            override fun run() {
                binding?.rvPremiereInclude?.movieViewPager2?.setCurrentItem(
                    binding!!.rvPremiereInclude.movieViewPager2.currentItem + ONE_VALUE, BOOLEAN_TRUE_VALUE
                )
                handler.postDelayed(this, 5000)
            }
        }
        handler.postDelayed(runnable, 5000)
    }

    private fun stopAutoScroll() {
            handler.removeCallbacks(runnable)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
    }

    private fun setUpAdapter(items: List<movie>) {
        //val adapter = MovieAdapter(items, this::onItemClicked)

        //binding?.rvPremiere?.adapter = adapter
        //binding?.rvPremiere?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onItemClicked(item: movie) {

    }
}