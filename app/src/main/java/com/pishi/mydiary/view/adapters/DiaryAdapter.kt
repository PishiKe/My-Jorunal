package com.pishi.mydiary.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pishi.mydiary.R
import com.pishi.mydiary.databinding.DiaryItemBinding
import com.pishi.mydiary.model.entities.MyDiary
import com.pishi.mydiary.view.fragments.HomeFragment

class DiaryAdapter (val fragment : Fragment) : RecyclerView.Adapter<DiaryAdapter.ViewHolder>(){

    private var diaryEntries : List<MyDiary> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.ViewHolder {

        val binding : DiaryItemBinding = DiaryItemBinding.inflate(LayoutInflater.from(fragment.context),
        parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.ViewHolder, position: Int) {

        val diaryEntry = diaryEntries[position]

        Glide.with(fragment)
            .load(diaryEntry.image)
            .centerCrop()
            .into(holder.ivImage)

        holder.tvTitle.text = diaryEntry.title

        holder.ibMenu.setOnClickListener {

            val popup = PopupMenu(fragment.context, holder.ibMenu)
            popup.menuInflater.inflate(R.menu.diary_item_options_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.edit_menu){
                    Toast.makeText(fragment.context, "edit diary clicked", Toast.LENGTH_SHORT).show()
                } else if (it.itemId == R.id.delete_menu){
                    if (fragment is HomeFragment){
                        fragment.deleteDiaryEntry(diaryEntry)
                    }

                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int {

        return diaryEntries.size
    }

    class ViewHolder (view : DiaryItemBinding): RecyclerView.ViewHolder(view.root){

        val ivImage = view.ivDiaryItem
        val tvTitle = view.tvDiaryTitle
        val ibMenu = view.ibMenu

    }

    fun diaryEntryList (list: List<MyDiary>){

        diaryEntries = list
        notifyDataSetChanged()
    }
}