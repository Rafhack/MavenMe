package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.License

class LicensesAdapter(private val licenses: ArrayList<License>) : RecyclerView.Adapter<LicensesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.licenses_item, parent, false))

    override fun getItemCount(): Int = licenses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvwLicenseName = itemView.findViewById<TextView>(R.id.licenses_item_tvw_name)
        private val tvwLicenseUrl = itemView.findViewById<TextView>(R.id.licenses_item_tvw_url)

        fun bind(position: Int) {
            val license = licenses[position]
            tvwLicenseName.visibility = if (license.name.isNotEmpty()) VISIBLE else GONE
            tvwLicenseUrl.visibility = if (license.url.isNotEmpty()) VISIBLE else GONE
            tvwLicenseName.text = license.name
            tvwLicenseUrl.text = license.url
        }
    }

}