package com.example.myapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Conflicts extends AppCompatActivity implements View.OnClickListener {
    ScrollView scrollView;
    LinearLayout linearLayout;
    Button next;
    ImageView plus;
    int count;
    FirebaseDatabase database;
    DatabaseReference ref,refPeople;
    SharedPreferences sp;
    Dialog dialog;
    ArrayList<String> people;
    FirebaseUser user;
    FirebaseAuth auth;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conflicts);
        scrollView= findViewById(R.id.scroll_view_conflicts);
        linearLayout= findViewById(R.id.ll_conflicts);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        refPeople = database.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        people = new ArrayList<>();
        sp = getSharedPreferences("currentProject",MODE_PRIVATE);
        refPeople.child("Users").child(user.getUid()).child(sp.getString("number","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>=6) {

                    retrieveObjects(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        next=findViewById(R.id.next_conflicts);
        plus=findViewById(R.id.plus_conflicts);
        next.setOnClickListener(this);
        plus.setOnClickListener(this);
        count=0;
        LinearLayout view=new LinearLayout(this);
        view.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);
        Button person1 = new Button(this);
        person1.setBackgroundResource(R.drawable.rectangle_1_shape);
        person1.setText("Name");
        person1.setTextSize(15);
        person1.setId(count);
        person1.setAllCaps(false);
        person1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Conflicts.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(1000,1200);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText=dialog.findViewById(R.id.edit_text_dialog);
                ListView listView=dialog.findViewById(R.id.list_view_dialog);

                // Initialize array adapter
                adapter=new ArrayAdapter<>(Conflicts.this, android.R.layout.simple_list_item_1,people);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        person1.setText(adapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });
        count++;
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(400, 140);
        paramstext.setMargins(20,10,20,10);
        person1.setLayoutParams(paramstext);
        view.addView(person1);
        ImageView arrow = new ImageView(this);
        arrow.setImageResource(R.drawable.arrow);
        view.addView(arrow);
        Button person2 = new Button(this);
        person2.setBackgroundResource(R.drawable.rectangle_1_shape);
        person2.setText("Name");
        person2.setTextSize(15);
        person2.setLayoutParams(paramstext);
        person2.setId(count);
        person2.setAllCaps(false);
        count++;
        person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Conflicts.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(1000,1200);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText=dialog.findViewById(R.id.edit_text_dialog);
                ListView listView=dialog.findViewById(R.id.list_view_dialog);

                // Initialize array adapter
                adapter=new ArrayAdapter<>(Conflicts.this, android.R.layout.simple_list_item_1,people);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        person2.setText(adapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });
        view.addView(person2);
        linearLayout.addView(view);
    }

    private void retrieveObjects(DataSnapshot snapshot) {
        for(int i=0;i<snapshot.child("people").getChildrenCount();i++){
            people.add((String) snapshot.child("people").child("person "+(i+1)).getValue());
        }
    }

    @Override
    public void onClick(View view) {
        if(view==next){
            Button test = findViewById(0);
            String name="";
            boolean flag = true;

            if(!test.getText().toString().equals("Name"))
                name=test.getText().toString();
            for(int i=0; i<count||flag == false; i++){
                Button button = findViewById(i);
                Toast.makeText(this, ""+button.getText().toString(), Toast.LENGTH_SHORT).show();
                if(button.getText().toString().equals("Name")){
                    Toast.makeText(this, "There are empty names", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
                else {
                    if(name.equals(button.getText().toString())){
                        Toast.makeText(this, "There is 1 person in different sides of conflict", Toast.LENGTH_SHORT).show();
                    }
                }
                name = button.getText().toString();
            }
            if(flag){
                startActivity(new Intent(this,Food.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
            Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
        }
        if(view==plus){
            LinearLayout view1=new LinearLayout(this);
            view1.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            params.gravity = Gravity.CENTER;
            view1.setLayoutParams(params);
            view1.setGravity(Gravity.CENTER);
            Button person1 = new Button(this);
            person1.setBackgroundResource(R.drawable.rectangle_1_shape);
            person1.setText("Name");
            person1.setTextSize(15);
            person1.setId(count);
            person1.setAllCaps(false);
            count++;
            person1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Dialog(Conflicts.this);
                    dialog.setContentView(R.layout.dialog_searchable_spinner);
                    dialog.getWindow().setLayout(1000,1200);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    EditText editText=dialog.findViewById(R.id.edit_text_dialog);
                    ListView listView=dialog.findViewById(R.id.list_view_dialog);

                    // Initialize array adapter
                    adapter=new ArrayAdapter<>(Conflicts.this, android.R.layout.simple_list_item_1,people);

                    // set adapter
                    listView.setAdapter(adapter);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // when item selected from list
                            // set selected item on textView
                            person1.setText(adapter.getItem(position));

                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    });
                }
            });
            LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(400, 140);
            paramstext.setMargins(20,10,20,10);
            person1.setLayoutParams(paramstext);
            view1.addView(person1);
            ImageView arrow = new ImageView(this);
            arrow.setImageResource(R.drawable.arrow);
            view1.addView(arrow);
            Button person2 = new Button(this);
            person2.setBackgroundResource(R.drawable.rectangle_1_shape);
            person2.setText("Name");
            person2.setAllCaps(false);
            person2.setTextSize(15);
            person2.setLayoutParams(paramstext);
            person2.setId(count);
            count++;
            person2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Dialog(Conflicts.this);
                    dialog.setContentView(R.layout.dialog_searchable_spinner);
                    dialog.getWindow().setLayout(1000,1200);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    EditText editText=dialog.findViewById(R.id.edit_text_dialog);
                    ListView listView=dialog.findViewById(R.id.list_view_dialog);

                    // Initialize array adapter
                    adapter=new ArrayAdapter<>(Conflicts.this, android.R.layout.simple_list_item_1,people);

                    // set adapter
                    listView.setAdapter(adapter);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // when item selected from list
                            // set selected item on textView
                            person2.setText(adapter.getItem(position));

                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    });
                }
            });
            view1.addView(person2);
            linearLayout.addView(view1);
        }
    }
}
