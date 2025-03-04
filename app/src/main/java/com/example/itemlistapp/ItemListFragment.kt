package com.example.itemlistapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemlistapp.adapter.RecyclerAdaptor
import com.example.itemlistapp.model.Item

class ItemListFragment : Fragment()
{

    private lateinit var adaptor: RecyclerAdaptor
    private lateinit var recyclerView: RecyclerView

    private var itemList = mutableListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val mainActivity = activity as? MainActivity

        itemList = mainActivity?.getItemList() ?: mutableListOf()

        adaptor = RecyclerAdaptor(itemList)
        recyclerView.adapter = adaptor

        adaptor.setOnClickListener(object : RecyclerAdaptor.OnItemClickListener
        {
            override fun onItemClick(position: Int)
            {
                Log.d("Item Clicked", "You clicked position $position")
                val bottomSheet = ItemDetailsBottomSheet(itemList[position], position)
                bottomSheet.show(parentFragmentManager, "ItemDetailsBottomSheet")
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItemList()
    {
        adaptor.notifyDataSetChanged() // TODO need to verify this
    }

    @SuppressLint("NotifyDataSetChanged")
    fun editedItemList(position: Int)
    {
        adaptor.notifyItemChanged(position)
    }
}
