package com.example.retofoh.ui.Fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.retofoh.R
import com.example.retofoh.databinding.FragmentLoginBinding
import com.example.retofoh.util.PreferenceManager
import com.example.retofoh.util.constants.DNNI
import com.example.retofoh.util.constants.EMEAL
import com.example.retofoh.util.constants.NAME
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var customDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLoginBinding.inflate(layoutInflater).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val gson = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gson)
        setUpView()
    }

    private fun setUpView() {
        binding?.editextCorreo?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isNotEmpty = s.toString().isNotEmpty()
                binding?.materialButtonLogin?.isEnabled = isNotEmpty
                binding?.materialButtonLogin?.isClickable = isNotEmpty
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding?.materialButtonInvitado?.setOnClickListener{
            val preferenceManager = PreferenceManager(requireContext())
            preferenceManager.saveData(DNNI, binding?.editextCorreo?.text.toString())
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToCandyStoreFragment()
            )
        }
        binding?.materialButtonLogin?.setOnClickListener {
            sigInGoogle()
        }
    }

    private fun sigInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.id, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val preferenceManager = PreferenceManager(requireContext())
                preferenceManager.saveData(NAME, account.displayName.toString())
                preferenceManager.saveData(EMEAL, account.email.toString())
                preferenceManager.saveData(DNNI, binding?.editextCorreo.toString())
                popUpWelcom(account.displayName.toString())
            } else {
                Toast.makeText(requireActivity(), it.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun popUpWelcom(name: String){
        customDialog = Dialog(requireActivity(), R.style.CustomAlertDialog)
        customDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog!!.setCancelable(false)
        customDialog!!.setContentView(R.layout.fragment_welcom)

        val tvTitle = customDialog!!.findViewById<TextView>(R.id.name) as TextView
        val btnNext = customDialog!!.findViewById<MaterialButton>(R.id.next_buttom) as MaterialButton

        tvTitle.setText(name)
        btnNext.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToCandyStoreFragment()
            )
            customDialog!!.dismiss()

        }
        customDialog!!.show()
    }
}