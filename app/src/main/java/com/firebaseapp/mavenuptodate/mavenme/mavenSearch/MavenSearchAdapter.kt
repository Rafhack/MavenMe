package com.firebaseapp.mavenuptodate.mavenme.mavenSearch

import android.annotation.SuppressLint
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.data.entities.Dependency
import java.text.SimpleDateFormat
import java.util.*

class MavenSearchAdapter(private val results: ArrayList<Dependency>, private val callback: ((Dependency) -> Unit)) :
        RecyclerView.Adapter<MavenSearchAdapter.ViewHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("MMM dd, yyyy")
    private val calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false))

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvwArtifactName = itemView.findViewById<TextView>(R.id.tvwArtifactName)
        private val tvwArtifactGroup = itemView.findViewById<TextView>(R.id.tvwArtifactGroup)
        private val tvwVersion = itemView.findViewById<TextView>(R.id.tvwVersion)
        private val tvwLastUpdated = itemView.findViewById<TextView>(R.id.tvwLastUpdated)
        private val imgWarning = itemView.findViewById<ImageView>(R.id.imgWarning)
        private val ctlItemRoot = itemView.findViewById<ConstraintLayout>(R.id.ctlItemRoot)

        fun bind(position: Int) {
            val item = results[position]

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvwArtifactName.text = Html.fromHtml(itemView.context.getString(R.string.name, item.artifactId), Html.FROM_HTML_MODE_LEGACY)
                tvwArtifactGroup.text = Html.fromHtml(itemView.context.getString(R.string.group, item.group), Html.FROM_HTML_MODE_LEGACY)
                tvwVersion.text = Html.fromHtml(itemView.context.getString(R.string.latest_version, item.currentVersion, item.versionCount.toString()), Html.FROM_HTML_MODE_LEGACY)
                calendar.timeInMillis = item.timeStamp
                tvwLastUpdated.text = Html.fromHtml(itemView.context.getString(R.string.last_updated, sdf.format(calendar.time)), Html.FROM_HTML_MODE_LEGACY)
            } else @Suppress("DEPRECATION") {
                tvwArtifactName.text = Html.fromHtml(itemView.context.getString(R.string.name, item.artifactId))
                tvwArtifactGroup.text = Html.fromHtml(itemView.context.getString(R.string.group, item.group))
                tvwVersion.text = Html.fromHtml(itemView.context.getString(R.string.latest_version, item.currentVersion, item.versionCount.toString()))
                calendar.timeInMillis = item.timeStamp
                tvwLastUpdated.text = Html.fromHtml(itemView.context.getString(R.string.last_updated, sdf.format(calendar.time)))
            }

            imgWarning.visibility = if (item.upToDate) GONE else VISIBLE
            ctlItemRoot.setBackgroundColor(if (item.upToDate) 0 else ResourcesCompat.getColor(itemView.resources,
                    R.color.errorColor, null))

            itemView.setOnClickListener { callback.invoke(item) }
        }
    }

}