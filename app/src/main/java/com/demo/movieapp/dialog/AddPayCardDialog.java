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
import com.demo.movieapp.model.OnlineCard;

public class AddPayCardDialog extends DialogFragment {
    View dialogView;

    public OnDialogConfirmListener confirmListener;

    public void setConfirmListener(OnDialogConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.add_card_dialog, null);

        EditText cardId = dialogView.findViewById(R.id.card_id);
        EditText cardUsername = dialogView.findViewById(R.id.card_username);
        EditText cardCvv = dialogView.findViewById(R.id.card_cvv);


        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (confirmListener == null) return;
                        OnlineCard card = new OnlineCard();
                        card.setCardNumber(cardId.getText().toString());
                        card.setUserName(cardUsername.getText().toString());
                        card.setCvv(cardCvv.getText().toString());
                        confirmListener.onConfirm(card);
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

    public AddPayCardDialog() {

    }

    public interface OnDialogConfirmListener {
        void onConfirm(OnlineCard onlineCard);
    }
}
