package com.example.retofoh.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.retofoh.R
import com.example.retofoh.databinding.FragmentPayBinding
import com.example.retofoh.domain.model.Complete
import com.example.retofoh.ui.ViewModel.PremierViewModel
import com.example.retofoh.ui.ViewModel.PayViewModel
import com.example.retofoh.ui.satate.ViewState
import com.example.retofoh.util.PreferenceManager
import com.example.retofoh.util.constants

class PayFragment : Fragment() {

    private var _binding: FragmentPayBinding? = null
    val binding get() = _binding

    private var dialog: AlertDialog? = null
    private lateinit var payViewModel: PayViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPayBinding.inflate(layoutInflater).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        payViewModel = ViewModelProvider(this).get(PayViewModel::class.java)
        setObserver()
        setUpView()
    }

    private fun setObserver() {
        payViewModel.eventState.observe(requireActivity()) { state ->
            when (state) {
                is ViewState.responseComplete ->{
                    println("Code: " + state.reponseComplete.resul_code)
                    if(state.reponseComplete.resul_code.equals("0")) {
                        showMessage(
                            getString(R.string.text_title_alert_pay),
                            getString(R.string.text_message_alert_pay)
                        )
                    }
                }
                else -> Unit
            }
        }
    }
    private fun setUpView() {
        setupDocumentTypeSpinner(binding!!.spinnerDocumentType)
        loadAndSetPreferences()
        binding?.buttonContinue?.setOnClickListener {
            validatePayU()
        }
    }

    private fun validatePayU() {


        goComplete("123456789")
    }

    private fun goComplete(operationDate: String) {
        val complete = Complete(
            name = binding?.editTextName?.text.toString(),
            mail = binding?.editTextEmail?.text.toString(),
            dni = binding?.editTextDocumentNumber?.text.toString(),
            operation_date = operationDate
        )
        payViewModel.potComplete(complete)
    }

    private fun loadAndSetPreferences() {
        val preferenceManager = PreferenceManager(requireContext())

        val name = preferenceManager.getData(constants.NAME).toString()
        val dni = preferenceManager.getData(constants.DNNI).toString()
        val email = preferenceManager.getData(constants.EMEAL).toString()
        val currency = preferenceManager.getData(constants.PRICE).toString()

        binding?.textViewTotalAmount?.setText("Total a cobrar: ${currency}")

        if (!name.isNullOrEmpty()) {
            binding?.editTextName?.setText(name)
            binding?.editTextName?.isEnabled = false
        }

        if (!dni.isNullOrEmpty()) {
            binding?.editTextDocumentNumber?.setText(dni)
            binding?.spinnerDocumentType?.setSelection(0)
            binding?.spinnerDocumentType?.isEnabled = false
            binding?.editTextDocumentNumber?.isEnabled = false
        }

        if (!email.isNullOrEmpty()) {
            binding?.editTextEmail?.setText(email)
            binding?.editTextEmail?.isEnabled = false
        }
    }

    private fun setupDocumentTypeSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.document_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar") { _, _ ->
            requireActivity().onBackPressed()
            dialog!!.dismiss()
        }
        dialog = builder.create()
        dialog!!.show()
    }
}