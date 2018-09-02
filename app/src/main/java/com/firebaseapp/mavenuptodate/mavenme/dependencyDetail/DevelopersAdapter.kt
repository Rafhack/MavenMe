package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.Developer

class DevelopersAdapter(private val developers: ArrayList<Developer>) : RecyclerView.Adapter<DevelopersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.developer_item, parent, false))

    override fun getItemCount(): Int = developers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvwDevName = itemView.findViewById<TextView>(R.id.developer_item_tvw_name)
        private val tvwDevEmail = itemView.findViewById<TextView>(R.id.developer_item_tvw_email)
        private val tvwDevId = itemView.findViewById<TextView>(R.id.developer_item_tvw_id)

        fun bind(position: Int) {
            val dev = developers[position]
            tvwDevName.visibility = if (dev.name.isNotEmpty()) VISIBLE else GONE
            tvwDevEmail.visibility = if (dev.email.isNotEmpty()) VISIBLE else GONE
            tvwDevId.visibility = if (dev.id.isNotEmpty()) VISIBLE else GONE
            tvwDevName.text = dev.name
            tvwDevEmail.text = dev.email
            tvwDevId.text = dev.id
        }
    }

}