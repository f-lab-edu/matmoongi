package com.matmoongi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

object PermissionDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val dialogView = inflater.inflate(R.layout.dialog_location_permission, container, false)

        dialogView.findViewById<View>(R.id.confirmButton).setOnClickListener { this.dismiss() }

        return dialogView
    }
}
