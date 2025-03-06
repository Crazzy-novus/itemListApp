
package com.example.itemlistapp.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itemlistapp.databinding.ListItemBinding
import com.example.itemlistapp.model.Item

class RecyclerAdaptor(
    private var itemList: MutableList<Item>
) : RecyclerView.Adapter<RecyclerAdaptor.MyViewHolder>() {

    private lateinit var onClickListener: OnItemClickListener
    var itemId = -1  // TODO Need to remove from global

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemDeleteIconClick(position: Int)
    }

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnItemClickListener) {
        onClickListener = listener
    }

    fun deleteItem(itemPosition : Int)
    {
        itemList.removeAt(itemPosition)
        notifyItemRemoved(itemPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onClickListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filteredItemList(filteredItemList: List<Item>) {
        itemList = filteredItemList as MutableList<Item> // TODO need to verify
        notifyDataSetChanged()
    }

    fun addItem(item : Item) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }


    class MyViewHolder(
        private val binding: ListItemBinding,
        private var listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
            binding.listItemName.text = item.itemName
            binding.listSkuNum.text = item.skuNumber.toString()
            binding.listStockQtn.text = item.stockQuantity.toString()
            binding.listItemImage.setImageURI(Uri.parse(item.itemImageUri))

            binding.deleteIcon.setOnClickListener()
            {
                listener.onItemDeleteIconClick(absoluteAdapterPosition)
            }
        }
    }
}
