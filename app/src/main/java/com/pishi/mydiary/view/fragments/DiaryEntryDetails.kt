package com.pishi.mydiary.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pishi.mydiary.databinding.FragmentDiaryEntryDetailsBinding

class DiaryEntryDetails : Fragment() {

    private lateinit var binding: FragmentDiaryEntryDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentDiaryEntryDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}