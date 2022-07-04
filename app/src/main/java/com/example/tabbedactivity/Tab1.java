package com.example.tabbedactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PhoneBookDB db;
    ArrayList<PhoneBook> phoneList = new ArrayList<>();
    RecyclerView recyclerView;
    PhoneBookAdapter adapter;
    TextView noDataText;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        adapter = new PhoneBookAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        noDataText = rootView.findViewById(R.id.noData_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = new PhoneBookDB(getActivity());



        FloatingActionButton addBtn = rootView.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
        storeDataInArrays();
        return rootView;
    }

    void storeDataInArrays() {
        Cursor cursor = db.readAllData();
        if(cursor.getCount()==0){
            noDataText.setVisibility(noDataText.VISIBLE);
        }else{
            noDataText.setVisibility(noDataText.GONE);

            while(cursor.moveToNext()){

                PhoneBook phone = new PhoneBook(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3));

                phoneList.add(phone);
                adapter.addItem(phone);

                adapter.notifyDataSetChanged();
            }
        }

    }
}