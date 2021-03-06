package com.lee.halu.du_an_1_mob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lee.halu.du_an_1_mob.Adapter.NameAdapter;
import com.lee.halu.du_an_1_mob.Model.Model;

import java.util.ArrayList;
import java.util.List;

import static com.lee.halu.du_an_1_mob.LoginActivity.username1;

public class UpdateDrinksNameFragment extends Fragment {
    private FloatingActionButton btnInsertDrinks;
    NameAdapter adapter;
    ListView listView;
    List<Model> models = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //chuyen layout --> view
        View view = inflater.inflate(R.layout.update_drinks_name_fragment, container, false);
        btnInsertDrinks = view.findViewById(R.id.btn_insert_drinks);
        listView = view.findViewById(R.id.list);
        myRef = database.getReference("User").child(username1).child("doUong");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot modelDataSnapshot :
                        dataSnapshot.getChildren()) {
                    Model model = modelDataSnapshot.getValue(Model.class);
                    models.add(model);
                    }

                Log.e("ssss", models.size() + "");
                adapter = new NameAdapter(models, getActivity());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(position);
                return true;
            }
        });
        btnInsertDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateDrinksActivity.class));
            }
        });
        return view;
    }

    public void showAlertDialog(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thay đổi dữ liệu");
        builder.setMessage("Bạn có muốn sửa hay xóa dữ liệu?");
        builder.setCancelable(false);

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT).show();
                delete(position);
                models.remove(position);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Intent intent = new Intent(getActivity(), UpdateDrinksNameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("iddrinksname", models.get(position).getIdzone());
                bundle.putString("drinksname", models.get(position).getZonename());
                bundle.putString("typedrinksname", models.get(position).getName2());
                bundle.putInt("drinksprice", models.get(position).getPrice());
                intent.putExtra("bundledrinksname", bundle);
                intent.putExtra("positiondrinksname", position);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void delete(int i) {
        myRef.child(models.get(i).getIdzone()).removeValue();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222) {
            if (resultCode == -1) {
                Model model = data.getParcelableExtra("modeldrinksname2");
                int position = data.getIntExtra("positiondrinksname3", -1);
                models.set(position, model);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
