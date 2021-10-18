package com.pishi.mydiary.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.pishi.mydiary.R
import com.pishi.mydiary.databinding.FragmentHomeBinding
import com.pishi.mydiary.view.activities.DiaryEntry

class HomeFragment : Fragment() {

    private var binding : FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.fabAddDiary.setOnClickListener {

            startActivity(Intent(requireActivity(), DiaryEntry::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.add_diary_entry, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.add_entry -> {
                startActivity(Intent(requireActivity(), DiaryEntry::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}