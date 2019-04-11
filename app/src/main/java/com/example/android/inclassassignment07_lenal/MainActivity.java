package com.example.android.inclassassignment07_lenal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase myDatabase;
    private DatabaseReference myRef;
    private EditText keyInput;
    private EditText valueInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyInput = (EditText) findViewById(R.id.keyInput);

        valueInput = (EditText) findViewById(R.id.valueInput);


        // Write a message to the database
        myDatabase = FirebaseDatabase.getInstance();
        myRef = myDatabase.getReference("practice");

        myRef.setValue("this is the practice message");
    }

    public void readData(View view) {
        // Read from the database
        myRef = myDatabase.getReference(keyInput.getText().toString());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String loadedData = dataSnapshot.getValue(String.class);
                    valueInput.setText(loadedData);
                } else {
                    valueInput.setText("");
                    Toast.makeText(MainActivity.this, "Cannot find key", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Error loading Firebase",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void writeData(View view) {
        String input = keyInput.getText().toString();
        String value = valueInput.getText().toString();

        myRef = myDatabase.getReference(input);
        myRef.setValue(value);

    }
}
