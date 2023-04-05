package com.vahan.cityweather.ui.city

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vahan.cityweather.city.City
import com.vahan.cityweather.databinding.ItemCityBinding

class CitiesAdapter(
    context: Context,
    private val items: MutableList<City>,
    private val onClicked: (City) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCityBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(cities: List<City>){
        items.clear()
        items.addAll(cities)
        // we use notifyDataSetChanged() because we set the whole list data here
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val item = items[layoutPosition]
                onClicked(item)
            }
        }

    }
}