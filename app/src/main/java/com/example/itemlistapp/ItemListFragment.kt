package com.example.itemlistapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itemlistapp.adapter.RecyclerAdaptor
import com.example.itemlistapp.databinding.FragmentItemListBinding
import com.example.itemlistapp.model.Item
import com.google.gson.Gson

class ItemListFragment : Fragment(), ItemDetailsBottomSheet.ItemEditCallBack, ItemSortDialogFragment.ItemSortCallBAck
{

    private var adaptor: RecyclerAdaptor? = null
    // Store Date in Storage
    private lateinit var binding: FragmentItemListBinding
    private var callBackListener : ItemDetailsBottomSheet.ItemEditCallBack? = null
    private var itemList = mutableListOf<Item>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        setFragmentResultListener("requestKey") { _, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            val result = bundle.getSerializable("bundleKey")  as Item// TODO Need to do
            // Do something with the result.
//            itemList.add(result)
            adaptor?.addItem(result)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View
    {
        Log.d("Activity", " On created")
        binding = FragmentItemListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        loadItemList()

        if (false)
        {
            binding.fab.setOnClickListener()
            {
                replaceFragment(fragmentId = R.id.details_fragment, ItemCreation())
            }
        }
        else
        {
            binding.fab.setOnClickListener ()
            {
                replaceFragment(fragmentId = R.id.list_fragment, ItemCreation())
            }
        }

        binding.searchView.editText.setOnEditorActionListener()
        { _, _, _ ->
//            binding.searchBar.setText(binding.searchView.text)
            binding.searchView.hide()
            false
        }
        // Show searchView when searchBar is clicked
        binding.searchBar.setOnClickListener()
        {
            binding.searchView.show()
        }
        // Handle text change in SearchView input
        binding.searchView.editText.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                filterList(s.toString())
            }
            override fun afterTextChanged(s: Editable?)
            {}
        })

        binding.filterIcon.setOnClickListener()
        {
            callBackListener = this
            val bottomSheet = ItemSortDialogFragment(this@ItemListFragment)
            bottomSheet.show(childFragmentManager, "ItemSortedDialogFragment")
        }

        binding.toolbar.setNavigationOnClickListener()
        {

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(false)

        adaptor = RecyclerAdaptor(itemList)
        binding.recyclerView.adapter = adaptor
        adaptor?.setOnClickListener(object : RecyclerAdaptor.OnItemClickListener
        {
            override fun onItemClick(position: Int)
            {
                Log.d("Item Clicked", "You clicked position $position")

                val bottomSheet = ItemDetailsBottomSheet(itemList[position], position, this@ItemListFragment)
                bottomSheet.show(parentFragmentManager, "ItemDetailsBottomSheet")
            }

            override fun onItemDeleteIconClick(position: Int)
            {
                MyApplication.getDatabase()?.itemDao()?.delete(item = itemList[position])
                adaptor?.deleteItem(position)

            }
        })

//        binding.fab.setOnClickListener()
//        {
//            replaceFragment(fragmentId = R.id.list_fragment, ItemCreation())
//        }
    }

    private fun replaceFragment(fragmentId : Int, fragment: Fragment)
    {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(fragmentId, fragment)
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()
        Log.d("this", "Activated")
    }

    private fun loadItemList()
    {
        itemList = MyApplication.getDatabase()?.itemDao()?.getAllItems() ?: mutableListOf()
    }

    override fun getEditedItem(itemObj : Item, position: Int)
    {
        itemList[position] = itemObj
        Log.d("hello", "helllo $itemObj, $position")
        adaptor?.notifyItemChanged(position)
    }

    override fun getItemSortBY(option: Int) {
        adaptor?.filteredItemList(getSortedItemList(option))
    }

    private fun getSortedItemList(option : Int) : List<Item>
    {
        return when(option)
        {
            0 -> itemList.sortedBy { it.itemName }
            1 -> itemList.sortedByDescending { it.itemName }
            else -> itemList
        }
    }

    private fun filterList(query: String?) {
        if (query.isNullOrEmpty()) {
            adaptor?.filteredItemList(itemList)
        } else {
            adaptor?.filteredItemList(itemList.filter { it.itemName.startsWith(query, ignoreCase = true) })
        }
    }
}
