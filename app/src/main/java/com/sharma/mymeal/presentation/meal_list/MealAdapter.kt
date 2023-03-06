package com.sharma.mymeal.presentation.meal_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sharma.mymeal.R
import com.sharma.mymeal.databinding.MealListItemBinding
import com.sharma.mymeal.domain.model.Meal

class MealAdapter(
    private val data: ArrayList<Meal>
) : RecyclerView.Adapter<MealAdapter.DataViewHolder>() {

    class DataViewHolder(
        private val binding: MealListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.model = meal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding: MealListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.meal_list_item,
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(data[position])

    fun addData(list: List<Meal>) {
        data.addAll(list)
    }
}