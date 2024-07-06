package com.example.retofoh.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retofoh.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    class CandyStoreFragment : Fragment() {

        private var _binding: FragmentLoginBinding? = null
        val binding get() = _binding
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
        }
    }
}