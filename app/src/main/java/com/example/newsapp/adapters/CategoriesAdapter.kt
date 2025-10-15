package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapp.databinding.ItemCategoryBinding
import com.example.newsapp.model.CategoryModel

class CategoriesAdapter(var categoryModel: List<CategoryModel?>, var onItemClickListener: (CategoryModel) -> Unit) :
    Adapter<CategoriesAdapter.categoryViewHolder>() {
    class categoryViewHolder(val binding: ItemCategoryBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return categoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryModel.size
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val category = categoryModel[position]
        holder.binding.categoryName.text = category?.title
        holder.binding.categoryImage.setImageResource(category!!.imageId)
        holder.binding.CardView.setOnClickListener {
            onItemClickListener.invoke(category)
        }
        holder.binding.CardView.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.binding.root.context,
                category.backgroundColorId
            )
        )

    }
}