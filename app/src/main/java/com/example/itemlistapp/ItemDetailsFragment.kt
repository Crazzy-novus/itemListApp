package com.example.itemlistapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.itemlistapp.databinding.ItemDetailsBinding

class ItemDetailsFragment : Fragment() {
    private lateinit var binding: ItemDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        // Use DataBindingUtil to inflate the layout
        binding =
            DataBindingUtil.inflate(inflater, R.layout.item_details, container, false)
        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        binding.item = itemObject
//
//        // Explicitly set the value for text fields as this data is not bound
//        binding.itemImage.setImageURI(Uri.parse(itemObject.itemImageUri))
//
//        // Set the visibility of the Edit icon
//        binding.editQuantityIcon.visibility = View.VISIBLE
//        binding.editQuantityIcon.setOnClickListener {
//
//            if (toggle)
//            {
//                binding.stockQuantity.visibility = View.GONE
//                binding.stockQuantityEt.visibility = View.VISIBLE
//                binding.editQuantityIcon.setImageResource(R.drawable.save_icon)
//                toggle = !toggle
//            }
//            else
//            {
//                //itemObject.stockQuantity = binding.stockQuantityEt.text.toString().toIntOrNull() ?: 0
//                MyApplication.getDatabase()?.itemDao()?.updateItem(itemObject)
//                Log.d("hello", "$itemObject")
//                callBackListener?.getEditedItem(itemObject, position)
//                binding.editQuantityIcon.setImageResource(R.drawable.edit_icon)
////                binding.stockQuantity.text = itemObject.stockQuantity.toString()
//                binding.stockQuantity.visibility = View.VISIBLE
//                binding.stockQuantityEt.visibility = View.GONE
//                toggle = !toggle
//            }
//        }
//


    }


}