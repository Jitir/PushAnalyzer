package errouane.benjamin.pushanalizer.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import errouane.benjamin.pushanalizer.Common;
import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.session.Session;


public class MoreStatsFragment extends ViewPagerFragment {
    private Button resetButton;
    private Button saveButton;
    private Button pushButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_stats, container, false);

        resetButton = (Button) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("This will reset your current session!")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Session.getInstance().reset();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }).show();
            }
        });

        saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSession();
            }
        });

        pushButton = (Button) view.findViewById(R.id.pushButton);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPush();
            }
        });

        return view;
    }

    private void addPush() {
        Session.getInstance().addPush();
    }

    private void writeStringToFile(String filename, String content) {
        // Check if SD-Card is available
        if(!Common.isExternalStorageAvailable(getActivity())) {
            return;
        }

        File file = new File(getActivity().getExternalFilesDir(null), filename);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print(content);
            pw.close();

            Log.e("PushAnalyzer", "wrote");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("PushAnalyzer", "File not found");
        }
    }

    private void saveSession() {
        final EditText editText = new EditText(getActivity());
        new AlertDialog.Builder(getActivity())
                .setTitle("Filename")
                .setMessage("Please choose a filename:")
                .setView(editText)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = editText.getText().toString();
                        if (!value.isEmpty()) {
                            writeStringToFile(value + ".nfo", Session.getInstance().toReadableString());
                            writeStringToFile(value + ".ext", Session.getInstance().toSimpleString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();
    }

    @Override
    public void newRotationData(RotationDataEvent event) {

    }

    @Override
    public void newPush() {

    }

    @Override
    public String getDescription() {
        return "More Stats";
    }

    @Override
    public void reset() {
        Log.e("sad", "d");
    }
}
