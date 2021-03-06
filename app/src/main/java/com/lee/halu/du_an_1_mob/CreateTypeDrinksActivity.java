package com.lee.halu.du_an_1_mob;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.halu.du_an_1_mob.Model.Model;

import static com.lee.halu.du_an_1_mob.LoginActivity.username1;

public class CreateTypeDrinksActivity extends AppCompatActivity {
    private Button btnCreateTypeDrinks;
    private Button btnBackToUpdateDrinks;
    private TextInputEditText edtTypeDrinksId;
    private TextInputEditText edtDrinksName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_type_drinks);
        init();
        btnCreateTypeDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String idtypedrinks = edtTypeDrinksId.getText().toString();
                final String typedrinksname = edtDrinksName.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User").child(username1).child("loaiDoUong");

                String typedrinksids = myRef.child(idtypedrinks).getKey();
                Model model = new Model(idtypedrinks, typedrinksname);
                myRef.child(typedrinksids).setValue(model);
                startActivity(new Intent(CreateTypeDrinksActivity.this, UpdateDrinksActivity.class));
                finish();
            }
        });
        btnBackToUpdateDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateTypeDrinksActivity.this, UpdateDrinksActivity.class));
                finish();
            }
        });
    }

    private void init() {
        btnCreateTypeDrinks =  findViewById(R.id.btn_create_type_drinks);
        btnBackToUpdateDrinks =  findViewById(R.id.btn_back_to_update_drinks);
        edtTypeDrinksId =  findViewById(R.id.edt_type_drinks_id);
        edtDrinksName =  findViewById(R.id.edt_drinks_name);

    }
}
