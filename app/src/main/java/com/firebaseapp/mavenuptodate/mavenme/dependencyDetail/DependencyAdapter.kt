package com.firebaseapp.mavenuptodate.mavenme.dependencyDetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.data.entities.dependencyDetail.ProjectDependency

class DependencyAdapter(private val dependencies: ArrayList<ProjectDependency>) : RecyclerView.Adapter<DependencyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dependency_item, parent, false))

    override fun getItemCount(): Int = dependencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvwArtifactName = itemView.findViewById<TextView>(R.id.dependency_item_tvw_artifact_name)
        private val tvwGroup = itemView.findViewById<TextView>(R.id.dependency_item_tvw_group_id)

        fun bind(position: Int) {
            val dependency = dependencies[position]
            tvwArtifactName.visibility = if (dependency.artifactId.isNotEmpty()) VISIBLE else GONE
            tvwGroup.visibility = if (dependency.groupId.isNotEmpty()) VISIBLE else GONE
            tvwArtifactName.text = dependency.artifactId
                    .plus(if (dependency.version.isNotEmpty()) " (${dependency.version})" else "")
                    .plus(if (dependency.scope.isNotEmpty()) " (${dependency.scope})" else "")
            tvwGroup.text = dependency.groupId
        }
    }

}