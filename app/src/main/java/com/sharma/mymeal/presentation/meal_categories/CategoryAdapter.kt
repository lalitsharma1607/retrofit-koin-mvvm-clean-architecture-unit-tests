package com.sharma.mymeal.presentation.meal_categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sharma.mymeal.R
import com.sharma.mymeal.databinding.CategoryListItemBinding
import com.sharma.mymeal.domain.model.Category

class CategoryAdapter(
    private val data: ArrayList<Category>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.DataViewHolder>() {

    class DataViewHolder(
        private val binding: CategoryListItemBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.model = category
            binding.root.setOnClickListener {
                onItemClickListener.onItemClicked(category.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding: CategoryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_list_item,
            parent,
            false
        )
        return DataViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(data[position])

    fun addData(list: List<Category>) {
        data.addAll(list)
    }
}

interface OnItemClickListener {
    fun onItemClicked(item: String)
}