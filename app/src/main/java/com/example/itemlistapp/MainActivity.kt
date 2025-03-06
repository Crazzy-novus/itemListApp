package com.example.itemlistapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.example.itemlistapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var isTablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(fragmentId = R.id.list_fragment, ItemListFragment())

        // Window Screen Size initialization
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass.compute(width/density, height/density)
        // Set the isTablet check to confirm which screen is involved
        isTablet = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

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
        }
        fragmentTransaction.commit()
    }

}
