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
import com.example.retofoh.domain.model.AdditionalValues
import com.example.retofoh.domain.model.Complete
import com.example.retofoh.domain.model.CreditCardInfo
import com.example.retofoh.domain.model.MerchantInfo
import com.example.retofoh.domain.model.OrderInfo
import com.example.retofoh.domain.model.PayerInfo
import com.example.retofoh.domain.model.PaymentTransaction
import com.example.retofoh.domain.model.TransactionInfo
import com.example.retofoh.domain.model.TxValue
import com.example.retofoh.ui.ViewModel.PayViewModel
import com.example.retofoh.ui.satate.ViewState
import com.example.retofoh.util.PreferenceManager
import com.example.retofoh.util.constants


class PayFragment : Fragment() {

    private var _binding: FragmentPayBinding? = null
    val binding get() = _binding

    private var dialog: AlertDialog? = null
    private lateinit var payViewModel: PayViewModel
    private var name: String = ""
    private var dni: String = ""
    private var email: String = ""
    private var currency: String = ""

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
                is ViewState.ShowLoader ->{
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                is ViewState.HideLoader ->{
                    binding?.progressBar?.visibility = View.GONE
                }
                is ViewState.responseComplete ->{
                    println("Code: " + state.reponseComplete.resul_code)
                    if(state.reponseComplete.resul_code.equals("0")) {
                        showMessage(
                            getString(R.string.text_title_alert_pay),
                            getString(R.string.text_message_alert_pay)
                        )
                    }
                }
                is ViewState.responsePayU -> {
                    goComplete(state.paymentResponse.transactionResponse.operationDate.toString())
                }
                else -> Unit
            }
        }
    }
    private fun setUpView() {
        setupDocumentTypeSpinner(binding!!.spinnerDocumentType)
        loadAndSetPreferences()
        binding?.buttonContinue?.setOnClickListener {
            if (validateFields()) {
                validatePayU()
            }else{
                showMessageValid(getString(R.string.text_title_alert_pay_valid), getString(R.string.text_message_alert_pay_valid))
            }
        }
    }

    private fun validatePayU() {
        payViewModel.potPayupaymentTransaction(createPaymentTransaction())
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

        name = preferenceManager.getData(constants.NAME).toString()
        dni = preferenceManager.getData(constants.DNNI).toString()
        email = preferenceManager.getData(constants.EMEAL).toString()
        currency = preferenceManager.getData(constants.PRICE).toString()
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

    private fun showMessageValid(title: String, message: String) {
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

    fun createPaymentTransaction(): PaymentTransaction {
        val transaction = TransactionInfo(
            order = OrderInfo(
                language = "es",
                additionalValues = AdditionalValues(
                    TX_VALUE = TxValue(
                        value = currency,
                        currency = "PEN"
                    )
                )
            ),
            payer = PayerInfo(
                fullName = name,
                emailAddress = email,
                contactPhone = "998877665",
                dniNumber = dni
            ),
            creditCard = CreditCardInfo(
                number = binding?.editTextCardNumber?.text.toString(),
                securityCode = binding?.editTextCVV?.text.toString(),
                expirationDate = binding?.editTextExpiryDate?.text.toString(),
                name = "APPROVED"
            ),
            type = "AUTHORIZATION",
            paymentMethod = "VISA",
            paymentCountry = "PE"
        )

        val merchant = MerchantInfo(
            apiKey = "4Vj8eK4rloUd272L48hsrarnUA",
            apiLogin = "pRRXKOl8ikMmt9u"
        )

        val paymentTransaction = PaymentTransaction(
            test = true,
            language = "es",
            command = "SUBMIT_TRANSACTION",
            merchant = merchant,
            transaction = transaction
        )

        return paymentTransaction
    }

    private fun validateFields(): Boolean {
        val cardNumber = binding?.editTextCardNumber?.text.toString().trim()
        val cvv = binding?.editTextCVV?.text.toString().trim()
        val expiryDate = binding?.editTextExpiryDate?.text.toString().trim()
        val name = binding?.editTextName?.text.toString().trim()
        val dni = binding?.editTextDocumentNumber?.text.toString().trim()
        val email = binding?.editTextEmail?.text.toString().trim()

        return cardNumber.isNotEmpty() && cvv.isNotEmpty() && expiryDate.isNotEmpty() &&
                name.isNotEmpty() && dni.isNotEmpty() && email.isNotEmpty()
    }
}