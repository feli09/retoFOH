package com.example.retofoh.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retofoh.R
import com.example.retofoh.databinding.FragmentCandyStoreBinding
import com.example.retofoh.domain.model.Combo
import com.example.retofoh.ui.Adapter.CandyAdapter
import com.example.retofoh.ui.ViewModel.CandyViewModel
import com.example.retofoh.ui.satate.ViewState
import com.example.retofoh.util.PreferenceManager
import com.example.retofoh.util.constants

class CandyStoreFragment : Fragment() {

    private var _binding: FragmentCandyStoreBinding? = null

    private lateinit var candyViewModel: CandyViewModel

    private var dialog: AlertDialog? = null
    private var price: Double = 0.0
    private var textprice: String = ""

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
        binding?.btnAction?.setOnClickListener {
            if (price > 0.0){
                val preferenceManager = PreferenceManager(requireContext())
                preferenceManager.saveData(constants.PRICE, price.toString())
                findNavController().navigate(
                    CandyStoreFragmentDirections.actionCandyStoreFragmentToPayFragment()
                )
            }else{
                showMessage(getString(R.string.text_title_alert), getString(R.string.text_message_alert))
            }

        }
    }

    private fun setUpAdapter(items: List<Combo>) {
        val adapter = CandyAdapter(items) { count, totalPrice ->
            updateSelectionInfo(count, totalPrice)
        }

        binding?.rvPremiere?.adapter = adapter
        binding?.rvPremiere?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateSelectionInfo(count: Int, totalPrice: Double) {
        binding?.tvSelectedItems?.text = "Items: $count"
        binding?.tvTotalPrice?.text = "Total: S/${String.format("%.2f", totalPrice)}"
        price = totalPrice
        textprice = "S/${String.format("%.2f", totalPrice)}"
    }

    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar") { _, _ ->
            dialog!!.dismiss()
        }
        dialog = builder.create()
        dialog!!.show()
    }
}