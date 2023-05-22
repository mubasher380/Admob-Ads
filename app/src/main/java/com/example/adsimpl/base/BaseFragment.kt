package com.example.adsimpl.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.adsimpl.R
import com.example.adsimpl.other_utils.extentions.Permissions.getPermissionString

abstract class BaseFragment : Fragment() {

    val TAG = "BaseFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ${activity?.packageName}")

    }


    fun postAnalytic(event: String?) {
        (activity as BaseActivity).postAnalytic(event)
    }

    var toast: Toast? = null
    fun showToastShort(msg: String?) {

        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast?.show()

    }

    fun showToastLong(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    var actionOnPermission: ((granted: Boolean) -> Unit)? = null
    var isAskingPermissions = false
    var pId: Int? = null

    val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Handle Permission granted/rejected
            if (permissions[pId?.let { getPermissionString(it) }] == true
            ) {
                actionOnPermission?.let { it(true) }

            } else {
                // Permission was denied. Display an error message.
                pId?.let { checkUserRequestedNotAskAgain(it) }
            }


        }


    fun handlePermission(permissionId: Int, callback: (granted: Boolean) -> Unit) {
        actionOnPermission = null
        actionOnPermission = callback
        pId = permissionId
        val arr = arrayOf(
            getPermissionString(permissionId)
        )

        activityResultLauncher.launch(arr)


    }

    fun checkUserRequestedNotAskAgain(permissionId: Int) {
        val rationalFlagContacts =
            shouldShowRequestPermissionRationale(getPermissionString(permissionId))
        if (!rationalFlagContacts) {
            showRationalDialog()
        }
    }

    private fun showRationalDialog() {

        val mBuilder = context?.let { AlertDialog.Builder(it) }

        mBuilder?.setTitle(getString(R.string.permission_required))
        mBuilder?.setMessage(getString(R.string.permission_detail))
        mBuilder?.setPositiveButton(
            R.string.ok
        ) { dialog, _ ->
            openPermissionSettings()
            dialog?.dismiss()
        }

        mBuilder?.setNegativeButton("No")
        { dialog, _ ->
            dialog.dismiss()
        }
        mBuilder?.show()

    }


    fun openPermissionSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        getPermissionResult.launch(intent)
    }


    private val getPermissionResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {

                Toast.makeText(
                    context,
                    getString(R.string.permission_granted),
                    Toast.LENGTH_SHORT
                ).show()
                actionOnPermission?.let { it1 -> it1(true) }
            }
            actionOnPermission?.let { it1 -> it1(false) }


        }


}