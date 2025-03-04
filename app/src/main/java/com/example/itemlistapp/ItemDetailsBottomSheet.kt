package com.example.itemlistapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itemlistapp.databinding.FragmentItemCreationBinding
import com.example.itemlistapp.model.Item
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDetailsBottomSheet(
    private var itemObject: Item,
    private var position: Int
) : BottomSheetDialogFragment()
{
    private var _binding: FragmentItemCreationBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentItemCreationBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.item = itemObject

//        binding.skuNumberEt.setText(itemObject.skuNumber.toString())
//        binding.stockInQuantityEt.setText(itemObject.stockQuantity.toString())
        binding.itemImage.setImageURI(Uri.parse(itemObject.itemImageUri))


        // Set the visibility of the Edit icon
        binding.editQuantityIcon.visibility = View.VISIBLE

        // Make the the Edit Text Field to read only as only text View needed
        binding.itemNameEt.isEnabled = false
        binding.skuNumberEt.isEnabled = false
        binding.stockInQuantityEt.isEnabled = false
        binding.descriptionEt.isEnabled = false
        binding.saveBtn.visibility = View.INVISIBLE

        binding.editQuantityIcon.setOnClickListener {
            binding.stockInQuantityEt.isEnabled = true
            binding.saveBtn.visibility = View.VISIBLE
        }

        binding.saveBtn.setOnClickListener {
            // Set the new quantity to the object
            itemObject.stockQuantity = binding.stockInQuantityEt.text.toString().toIntOrNull() ?: 0

            // Call the OnItemEdited Function to refresh the RecyclerView
            (activity as? MainActivity)?.onItemEdited(itemObject, position)
            dismiss()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
