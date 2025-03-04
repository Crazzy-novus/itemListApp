package com.example.itemlistapp.adapter

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itemlistapp.R
import com.example.itemlistapp.model.Item

class RecyclerAdaptor(
    private var itemList: MutableList<Item>,

) : RecyclerView.Adapter<RecyclerAdaptor.MyViewHolder>()
{
    private lateinit var onClickListener: OnItemClickListener

    interface OnItemClickListener
    {
        fun onItemClick(position: Int)
    }

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnItemClickListener ) {
        onClickListener = listener
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder
    {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,false)
        return MyViewHolder(itemView, onClickListener)
    }

    override fun getItemCount(): Int
    {
        return itemList.size
    }

    @SuppressLint("SetTextI18n", "Recycle")
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    )
    {
        val currentItem = itemList[position]
        holder.itemName.text = currentItem.itemName
        holder.itemSkuNumber.text = currentItem.skuNumber.toString()
        holder.itemStockQuantity.text = currentItem.stockQuantity.toString()
        holder.itemImage.setImageURI(Uri.parse(currentItem.itemImageUri))

    }

    class MyViewHolder (
        itemView : View,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView)
    {
         val itemName : TextView = itemView.findViewById(R.id.list_item_name)
        val itemSkuNumber : TextView = itemView.findViewById(R.id.list_sku_num)
        val itemStockQuantity : TextView = itemView.findViewById(R.id.list_stock_qtn)
        val itemImage :ImageView = itemView.findViewById(R.id.list_item_image)

        init {
            itemView.setOnClickListener()
            {
                listener.onItemClick(
                    position = getAbsoluteAdapterPosition(),
                )
            }
        }
    }
}