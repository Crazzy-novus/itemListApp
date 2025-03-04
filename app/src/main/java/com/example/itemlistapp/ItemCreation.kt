
package com.example.itemlistapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.itemlistapp.databinding.FragmentItemCreationBinding
import com.example.itemlistapp.model.Item

class ItemCreation : Fragment()
{

    private lateinit var binding: FragmentItemCreationBinding
    var itemImageUri : Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View
    {
        // Use DataBindingUtil to inflate the layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_creation, container, false)

        // Initialize an empty Item object for binding
        val item = Item(itemImageUri = "", itemName = "", skuNumber = 0, stockQuantity = 0, description = "")
        binding.item = item

        // Registers a photo picker activity launcher in single-select mode
        val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null)
            {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context?.contentResolver?.takePersistableUriPermission(uri, flag)
                // Persist permissions
                binding.itemImage.setImageURI(uri)
                itemImageUri = uri // Update the item's image URI

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
            item.itemName = binding.itemNameEt.text.toString()
            item.skuNumber = binding.skuNumberEt.text.toString().toIntOrNull() ?: 0
            item.stockQuantity = binding.stockInQuantityEt.text.toString().toIntOrNull() ?: 0
            item.description = binding.descriptionEt.text.toString()
            item.itemImageUri = itemImageUri.toString()

            // Pass new item to MainActivity
            (activity as? MainActivity)?.onItemCreated(item.copy())
//            binding.item = resetDefault()

            binding.itemNameEt.setText("")
            binding.skuNumberEt.setText("")
            binding.stockInQuantityEt.setText("")
            binding.descriptionEt.setText("")
            binding.itemImage.setImageResource(R.drawable.default_image)
        }

        return binding.root
    }
    // TODO verify which is recommended

//    private fun resetDefault( ) : Item
//    {
//        return Item(itemName = "", skuNumber = 0, stockQuantity = 0, description = "")
//
//    }

}
