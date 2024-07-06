package com.example.retofoh.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retofoh.databinding.ItemCandyBinding
import com.example.retofoh.domain.model.Combo
import com.example.retofoh.util.constants.BOOLEAN_FALSE_VALUE

class CandyAdapter(
    private val items: List<Combo>,
    private val onSelectionChanged: (Int, Double) -> Unit
) :
    RecyclerView.Adapter<CandyAdapter.ViewHolder>() {

    private val selectedItems = mutableListOf<Combo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCandyBinding.inflate(LayoutInflater.from(parent.context), parent, BOOLEAN_FALSE_VALUE)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position % items.size])
    }

    inner class ViewHolder(private val binding: ItemCandyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Combo) {
            binding.titleCandy.text = item.name
            binding.descriptionCandy.text = item.description
            binding.priceCandy.text = "${item.currency} ${item.price}"

            binding.selectCheckBox.isChecked = selectedItems.contains(item)

            binding.selectCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(item)
                } else {
                    selectedItems.remove(item)
                }
                onSelectionChanged(selectedItems.size, selectedItems.sumOf { it.price.toDouble() })
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

