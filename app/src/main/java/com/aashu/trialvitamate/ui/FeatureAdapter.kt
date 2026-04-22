package com.aashu.trialvitamate.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aashu.trialvitamate.databinding.ItemFeatureBinding

class FeatureAdapter(
    private val features: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFeatureBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeatureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feature = features[position]

        holder.binding.title.text = feature

        holder.binding.root.setOnClickListener {
            onClick(feature)
        }
    }

    override fun getItemCount() = features.size
}