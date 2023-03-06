package com.example.tfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportBugActivity extends AppCompatActivity {


    private boolean checked = true;
    @SuppressLint({"SetTextI18n", "IntentReset"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch changer = findViewById(R.id.BugOrFeedback);
        Button sendButton = findViewById(R.id.Send);
        EditText feedbackOrBug = findViewById(R.id.textInput);
        EditText bugTitle = findViewById(R.id.inputTitle);



        changer.setOnClickListener(view -> {
            changer.setChecked(checked);
            checked = !checked;
            if(!checked){
                changer.setText("Send feedback");
                sendButton.setText("Send feedback");
                feedbackOrBug.setHint("Write here your feedback");
            }else{
                changer.setText("Report a bug");
                sendButton.setText("Send bug report");
                feedbackOrBug.setHint("Write here your bug report");
            }
        });

        sendButton.setOnClickListener(view -> {
            if(!feedbackOrBug.getText().toString().equals("") && !bugTitle.getText().toString().equals("")){
                Uri uri = Uri.parse("mailto:telahelsabio@gmail.com")
                        .buildUpon()
                        .appendQueryParameter("subject", "[" + (!checked ? "Feedback" : "Bug report") + "] " + bugTitle.getText().toString())
                        .appendQueryParameter("body", feedbackOrBug.getText().toString())
                        .appendQueryParameter("to", "telahelsabio@gmail.com")
                        .build();
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("message/rfc822");
                intent.setData(uri); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
            }else {
                    Toast.makeText(ReportBugActivity.this, "You need to add title and body to the report", Toast.LENGTH_LONG).show();
                }
            });



    }
}