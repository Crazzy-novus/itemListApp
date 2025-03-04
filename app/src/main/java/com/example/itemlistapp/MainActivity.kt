package com.example.itemlistapp


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.example.itemlistapp.databinding.ActivityMainBinding
import com.example.itemlistapp.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var isTablet: Boolean = false
    private val itemList = mutableListOf<Item>()
    private lateinit var itemListFragment: ItemListFragment
    private lateinit var itemCreation: ItemCreation

    // Store Date in Storage
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize the shared preference
        sharedPreferences = getSharedPreferences("ExpenseTrackerPrefs", MODE_PRIVATE)
        loadItemList()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener() {
            removeFragment(fragmentId = R.id.list_fragment, itemCreation)
        }

        // Window Screen Size initialization
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass.compute(width/density, height/density)
        // Set the isTablet check to confirm which screen is involved
        isTablet = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT


        itemCreation = ItemCreation()

        if (isTablet)
        {
            binding.fab.setOnClickListener()
            {
                replaceFragment(fragmentId = R.id.details_fragment, itemCreation)
            }
        }
        else
        {
            binding.fab.setOnClickListener ()
            {
                replaceFragment(fragmentId = R.id.list_fragment, itemCreation)
            }
        }

        itemListFragment = ItemListFragment()
        replaceFragment(fragmentId = R.id.list_fragment, itemListFragment)


    }

    override fun onStop() {
        super.onStop()
        saveItemList()
    }

    private fun replaceFragment(fragmentId : Int, fragment: Fragment)
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isTablet)
        {
            fragmentTransaction.replace(fragmentId, fragment)
        }
        else
        {
            fragmentTransaction.add(R.id.list_fragment, fragment)

            // To hide search Bar and FAB in item Creation Fragment
            val toggle = fragment != itemCreation
            toggleView(toggle)
        }

        fragmentTransaction.commit()

    }

    private fun removeFragment(fragmentId : Int, fragment: Fragment)
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isTablet)
        {
            fragmentTransaction.replace(fragmentId, fragment)
        }
        else
        {
            fragmentTransaction.remove(fragment)
        }

        fragmentTransaction.commit()
        // To make the SearchBar and FAB visible if item creation fragment is removed
        toggleView(true)
    }

    private fun toggleView(toggle : Boolean)
    {
        if (toggle) {
            binding.toolbar.title = getString(R.string.app_name)
            binding.fab.visibility = View.VISIBLE
            binding.searchBar.visibility = View.VISIBLE

        } else {
            binding.toolbar.title = "Item Creation"
            binding.fab.visibility = View.GONE
            binding.searchBar.visibility = View.GONE
        }

    }

    // Function to handle new item creation
    fun onItemCreated(item: Item) {
        itemList.add(item) // Add item to list
        itemListFragment.updateItemList() // Notify RecyclerView of new item // TODO need to check
        if (!isTablet)
        {
            removeFragment(fragmentId = R.id.list_fragment, itemCreation)
        }
    }

    fun onItemEdited(item: Item, position: Int) {
        itemList[position] = item
        itemListFragment.editedItemList(position)
    }

    // Function to get current list
    fun getItemList(): MutableList<Item> {
        return itemList
    }

    private fun saveItemList() {
        val editor = sharedPreferences.edit()

        if (itemList.isNotEmpty())
        {
            // Initialize json to store list in shared preference
            val gson = Gson()
            val json = gson.toJson(itemList)
            editor.putString("ItemList", json)
        }
        editor.apply()
    }

    // Load the List of expense When activity creates
    private fun loadItemList()
    {
        val json = sharedPreferences.getString("ItemList", null) ?: return // Return null if no preference is available

        // If json is not null decode the json string to ExpenseRecordObject
        val gson = Gson()
        val type = object : TypeToken<MutableList<Item>>() {}.type
        val savedList = gson.fromJson<MutableList<Item>>(json, type)  ?: mutableListOf()

        itemList.clear()
        itemList.addAll(savedList)
    }
}
