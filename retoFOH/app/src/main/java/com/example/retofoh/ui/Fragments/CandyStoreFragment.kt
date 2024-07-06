package com.example.retofoh.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retofoh.databinding.FragmentCandyStoreBinding
import com.example.retofoh.domain.model.Combo
import com.example.retofoh.ui.Adapter.CandyAdapter
import com.example.retofoh.ui.ViewModel.CandyViewModel
import com.example.retofoh.ui.satate.ViewState

class CandyStoreFragment : Fragment() {

    private var _binding: FragmentCandyStoreBinding? = null

    private lateinit var candyViewModel: CandyViewModel


    val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCandyStoreBinding.inflate(layoutInflater).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        candyViewModel = ViewModelProvider(this).get(CandyViewModel::class.java)
        setObserver()
        setUpView()
    }

    private fun setObserver() {
        candyViewModel.eventState.observe(requireActivity()) { state ->
            when (state) {
                is ViewState.ListCandy ->{
                    setUpAdapter(state.listCandy)
                }

                else -> Unit
            }
        }
    }
    private fun setUpView() {
        candyViewModel.getCandys()
    }

    private fun setUpAdapter(items: List<Combo>) {
        val adapter = CandyAdapter(items, this::onItemClicked) { count, totalPrice ->
            updateSelectionInfo(count, totalPrice)
        }

        binding?.rvPremiere?.adapter = adapter
        binding?.rvPremiere?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onItemClicked(item: Combo) {
        // Manejar el clic en el item si es necesario
    }

    private fun updateSelectionInfo(count: Int, totalPrice: Double) {
        binding?.tvSelectedItems?.text = "Items: $count"
        binding?.tvTotalPrice?.text = "Total: S/${String.format("%.2f", totalPrice)}"
    }
}