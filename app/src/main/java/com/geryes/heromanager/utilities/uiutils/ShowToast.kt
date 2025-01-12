package com.geryes.heromanager.utilities.uiutils

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, toastMessage: String) {
    Toast.makeText(
        context,
        toastMessage,
        Toast.LENGTH_SHORT
    ).show()
}