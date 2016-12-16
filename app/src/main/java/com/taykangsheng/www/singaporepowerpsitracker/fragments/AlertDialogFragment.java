package com.taykangsheng.www.singaporepowerpsitracker.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
    String message = null;
    String positive_message = null;
    String negative_message = null;
    AlertDialogListener mListener;

    public static AlertDialogFragment newInstance(String message, String positive, String negative) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("positive", positive);
        args.putString("negative", negative);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString("message");
            positive_message = getArguments().getString("positive");
            negative_message = getArguments().getString("negative");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setPositiveButton(positive_message, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                mListener.onDialogPositiveClick(AlertDialogFragment.this);
            }
        });
        builder.setNegativeButton(negative_message, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                mListener.onDialogNegativeClick(AlertDialogFragment.this);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface AlertDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AlertDialogListener) {
            mListener = (AlertDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
