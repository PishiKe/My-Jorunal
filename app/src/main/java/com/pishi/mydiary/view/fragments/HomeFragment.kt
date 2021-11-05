package com.pishi.mydiary.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pishi.mydiary.R
import com.pishi.mydiary.application.MyDiaryApplication
import com.pishi.mydiary.databinding.FragmentHomeBinding
import com.pishi.mydiary.model.entities.MyDiary
import com.pishi.mydiary.view.activities.DiaryEntry
import com.pishi.mydiary.view.activities.MainActivity
import com.pishi.mydiary.view.adapters.DiaryAdapter
import com.pishi.mydiary.viewmodel.MyDiaryViewModel
import com.pishi.mydiary.viewmodel.MyDiaryViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var _binding : FragmentHomeBinding
    private lateinit var diaryAdapter : DiaryAdapter

    private val myDiaryViewModel : MyDiaryViewModel by viewModels {
        MyDiaryViewModelFactory((requireActivity().application as MyDiaryApplication).repository)
    }

    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.rvHome.layoutManager = LinearLayoutManager(requireActivity())

        diaryAdapter = DiaryAdapter(this)

        _binding.rvHome.adapter = diaryAdapter

        myDiaryViewModel.allDiaryList.observe(viewLifecycleOwner){
            entries ->
                entries.let {
                    if (it.isNotEmpty()){
                        _binding.rvHome.visibility = View.VISIBLE
                        _binding.tvNoEntriesAdded.visibility = View.INVISIBLE

                        diaryAdapter.diaryEntryList(it)
                    }
                    else{
                        _binding.rvHome.visibility = View.INVISIBLE
                        _binding.tvNoEntriesAdded.visibility =  View.GONE
                    }
                }
        }


        _binding.fabAddDiary.setOnClickListener {

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

    fun deleteDiaryEntry(diaryEntry: MyDiary){

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.delete_entry))
        builder.setMessage(resources.getString(R.string.msg_delete_entry, diaryEntry.title))
        builder.setIcon(ResourcesCompat.getDrawable(resources,R.drawable.ic_warning,null))
        builder.setPositiveButton(resources.getString(R.string.yes)){ dialogInterface, _ ->
            myDiaryViewModel.delete(diaryEntry)
            dialogInterface.dismiss()
        }

        builder.setNegativeButton(resources.getString(R.string.no)){ dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


}