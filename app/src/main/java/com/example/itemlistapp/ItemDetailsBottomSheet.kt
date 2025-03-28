package com.example.itemlistapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.itemlistapp.databinding.ItemDetailsBinding
import com.example.itemlistapp.model.Item
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDetailsBottomSheet(
    private var itemObject: Item,
    private var position: Int,
    private var callBackListener : ItemEditCallBack? = null
) : BottomSheetDialogFragment() {


    private var toggle = true // To switch icon

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        return view
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.bottomSheetFragment, ItemDetailsFragment())
        fragmentTransaction.commit()

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
    }



    interface ItemEditCallBack
    {
        fun getEditedItem(itemObj : Item, position: Int)
    }
}
