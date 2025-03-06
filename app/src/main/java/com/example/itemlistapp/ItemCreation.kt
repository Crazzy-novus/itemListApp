package com.example.itemlistapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.itemlistapp.databinding.FragmentItemCreationBinding
import com.example.itemlistapp.model.Item


class ItemCreation : Fragment() {

    private lateinit var binding: FragmentItemCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View
    {
        // Use DataBindingUtil to inflate the layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_creation, container, false)
        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener()
        {
            removeFragment()
        }

        // Initialize an empty Item object for binding
        var item = Item()
        binding.item = item
        // Registers a photo picker activity launcher in single-select mode
        val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null)
            {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context?.contentResolver?.takePersistableUriPermission(uri, flag)
                // Persist permissions
                binding.itemImage.setImageURI(uri)
                item.itemImageUri = uri.toString() // Update the item's image URI
                Log.d("PhotoPicker", "Selected URI: $uri")
            }
            else
            {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        binding.itemImage.setOnClickListener()
        {
            pickMedia.launch(PickVisualMediaRequest(ImageOnly))
        }

        binding.saveBtn.setOnClickListener()
        {

            if (item.itemName.isEmpty())
            {
                binding.nameErrorText.visibility = View.VISIBLE
            }
            else
            {
                binding.nameErrorText.visibility = View.GONE
                // Pass new item to MainActivity

                val result = MyApplication.getDatabase()?.itemDao()?.insertItem(item = item.copy()) // TODO need to add check
                Log.d("Item1234455","$result")
                setFragmentResult("requestKey", bundleOf("bundleKey" to item.copy()))
                removeFragment()

                // Reset fields
                item = Item() // TODO need to verify this is correct

            }
        }
    }

    private fun removeFragment()
    {
        parentFragmentManager.beginTransaction().remove( this).commitNow()
    }

    override fun onPause() {
        super.onPause()
        Log.d("On Pause Called", "On Pause Fragment Item Creation")
    }
}

