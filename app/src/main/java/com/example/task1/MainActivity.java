package com.example.task1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mResult;
    private Button mButton;
    private String mString;
    private InputStream mInputStream;
    private BufferedReader mBufferedReader;
    private StringBuffer mStringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        try {
            readFromResRaw();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTextView.setText(mStringBuffer);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = clearText(mStringBuffer.toString());
                List<String> list = Arrays.asList(str.split(" "));
                Set<String> words = new HashSet<>(list);
                StringBuffer stringBuffer = new StringBuffer();
                for(String word : words){
                    if(Collections.frequency(list, word) > 1) {
                        stringBuffer.append(word + ":" + Collections.frequency(list, word) + "\n");
                    }
                }
                mResult.setText(stringBuffer.toString());
            }
        });
    }

    private void readFromResRaw() throws IOException {
        mInputStream = this.getResources().openRawResource(R.raw.sometext);
        mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
        mStringBuffer = new StringBuffer();

        if(mInputStream !=null){
            while ((mString = mBufferedReader.readLine()) != null) {
                mStringBuffer.append(mString);
            }
        }
        mInputStream.close();
    }

    private String clearText(String s){
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    private void init(){
        mTextView = findViewById(R.id.text);
        mButton = findViewById(R.id.btn);
        mResult = findViewById(R.id.result);
    }


}
