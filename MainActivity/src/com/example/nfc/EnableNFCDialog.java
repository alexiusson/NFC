package com.example.nfc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class EnableNFCDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_enable_nfc)
               .setPositiveButton(R.string.dialog_settings, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                   }
               })
               .setNegativeButton(R.string.dialog_exit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}