package com.firebaseapp.mavenuptodate.mavenme.mavenSearch

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebaseapp.mavenuptodate.mavenme.R
import java.util.*

class MavenSuggestionAdapter(private val suggestions: ArrayList<String>, private val callback: ((String) -> Unit)) :
        RecyclerView.Adapter<MavenSuggestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.suggestion_item, parent, false))

    override fun getItemCount(): Int = suggestions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvwArtifactName = itemView.findViewById<TextView>(R.id.suggestion_item_tvw_suggestion)

        @SuppressLint("SimpleDateFormat")
        fun bind(position: Int) {
            tvwArtifactName.text = suggestions[position]
            itemView.setOnClickListener { callback(suggestions[position]) }
        }
    }

}