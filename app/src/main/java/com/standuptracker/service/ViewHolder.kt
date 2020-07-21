package com.standuptracker.service

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.standuptracker.R
import com.standuptracker.dto.Note


class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var mDateView: TextView? = null
    private var mNoteView: TextView? = null


    init {
        mDateView = itemView.findViewById(R.id.date_created)
        mNoteView = itemView.findViewById(R.id.note_content)
    }

    fun bind(note:Note) {
        mDateView?.text = note.dateCreated.toString()
        mNoteView?.text = note.content
    }

}