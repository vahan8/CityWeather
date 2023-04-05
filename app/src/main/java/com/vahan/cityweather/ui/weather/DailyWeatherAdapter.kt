package com.vahan.cityweather.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vahan.cityweather.R
import com.vahan.cityweather.databinding.ItemWeatherBinding
import com.vahan.cityweather.util.DateHelper
import com.vahan.cityweather.weather.DailyWeather

class DailyWeatherAdapter(
    private val context: Context,
    private val items: MutableList<DailyWeather>
) : RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWeatherBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvDate.text = DateHelper.getFormattedDate(item.date)
        holder.binding.tvTempMin.text = context.getString(R.string.temperature_celsius, item.minTemperature)
        holder.binding.tvTempMax.text = context.getString(R.string.temperature_celsius, item.maxTemperature)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(dailyWeatherList: List<DailyWeather>){
        items.clear()
        items.addAll(dailyWeatherList)
        // we use notifyDataSetChanged() because we set the whole list data here
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}