package com.pishi.mydiary.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pishi.mydiary.databinding.DiaryItemBinding
import com.pishi.mydiary.model.entities.MyDiary

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
            .into(holder.ivImage)

        holder.tvTitle.text = diaryEntry.title
    }

    override fun getItemCount(): Int {

        return diaryEntries.size
    }

    class ViewHolder (view : DiaryItemBinding): RecyclerView.ViewHolder(view.root){

        val ivImage = view.ivDiaryItem
        val tvTitle = view.tvDiaryTitle

    }

    fun diaryEntryList (list: List<MyDiary>){

        diaryEntries = list
        notifyDataSetChanged()
    }
}