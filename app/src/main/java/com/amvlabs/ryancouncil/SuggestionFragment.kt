package com.amvlabs.ryancouncil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amvlabs.ryancouncil.databinding.FragmentSuggestionBinding

class SuggestionFragment : Fragment() {
    lateinit var binding:FragmentSuggestionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSuggestionBinding.inflate(inflater,container,false)
        return binding.root
    }
}