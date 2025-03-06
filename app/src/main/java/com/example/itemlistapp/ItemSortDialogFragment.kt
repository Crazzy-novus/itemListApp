package com.example.itemlistapp

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itemlistapp.ItemDetailsBottomSheet.ItemEditCallBack
import com.example.itemlistapp.databinding.FragmentItemSortDialogListDialogBinding
import com.example.itemlistapp.model.Item


class ItemSortDialogFragment(private var callBackListener : ItemSortCallBAck? = null ) : BottomSheetDialogFragment()
{

    private lateinit var binding: FragmentItemSortDialogListDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemSortDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbSortAscending.setOnClickListener {
            callBackListener?.getItemSortBY (0) // 0 indicate ascending
            dismiss()
        }

        binding.rbSortDescending.setOnClickListener {
            callBackListener?.getItemSortBY (1) // 1 indicate Descending
            dismiss()
        }
    }
    interface ItemSortCallBAck
    {
        fun getItemSortBY(option : Int)
    }
}
