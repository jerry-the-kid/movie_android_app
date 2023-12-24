package com.demo.movieapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.demo.movieapp.R;

public class AddPayCardDialog extends DialogFragment {
    View dialogView;

    public AddPayCardDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.add_card_dialog, null);

        EditText title = dialogView.findViewById(R.id.title);
        EditText description = dialogView.findViewById(R.id.description);
        EditText date = dialogView.findViewById(R.id.date);


        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel the dialog.
//                        CertificateDialog.this.getDialog().cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
}
