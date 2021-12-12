package com.lapiragua.applapiragua.Home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lapiragua.applapiragua.R;
import com.lapiragua.applapiragua.funciones.ValidacionCampos;
import com.lapiragua.applapiragua.adapters.AdapterItemCardLugar;
import com.lapiragua.applapiragua.model.FirebaseReference;
import com.lapiragua.applapiragua.model.LugarPatrimonial;

import java.util.ArrayList;


public class FragmentBuscar extends Fragment implements SearchView.OnQueryTextListener{



    private SearchView searchVBuscar;
    private Button btn_buscar;

    private View view;
    private ValidacionCampos validar;

    private RecyclerView recyclerVData;
    private ArrayList<LugarPatrimonial> lugaresPatrimonialesList;
    private ArrayList<LugarPatrimonial> lugaresPatrimonialesListOriginal;
    private AdapterItemCardLugar adapter;

    private FirebaseFirestore db;

    ProgressDialog progressDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buscar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        event();
    }

    private void init() {
        btn_buscar = view.findViewById(R.id.button_buscarLugar);
        searchVBuscar = view.findViewById(R.id.searchView_palabrasBuscar);
        btn_buscar.setVisibility(View.VISIBLE);
        searchVBuscar.setVisibility(View.GONE);



        recyclerVData = view.findViewById(R.id.RecyclerView_listlugares);
        recyclerVData.setHasFixedSize(true);
        recyclerVData.setLayoutManager(new LinearLayoutManager(getContext()));


        db = FirebaseFirestore.getInstance();
        lugaresPatrimonialesList = new ArrayList<LugarPatrimonial>();
        lugaresPatrimonialesListOriginal = new ArrayList<LugarPatrimonial>();
        adapter = new AdapterItemCardLugar(getContext(), lugaresPatrimonialesList, lugaresPatrimonialesListOriginal);
        recyclerVData.setAdapter(adapter);

        //EventChangeListener();

    }

    private void EventChangeListener() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Recuperando datos");
        progressDialog.show();
        db.collection(FirebaseReference.DB_REFERENCE_LUGARES)
                .orderBy("nombre", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(getContext(), "FireBase error, "+ error.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){

                                lugaresPatrimonialesList.add(dc.getDocument().toObject(LugarPatrimonial.class));
                                lugaresPatrimonialesListOriginal.add(dc.getDocument().toObject(LugarPatrimonial.class));
                            }
                            adapter.notifyDataSetChanged();

                        }
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }



    private void event() {

        searchVBuscar.setOnQueryTextListener(this);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventChangeListener();
                searchVBuscar.setVisibility(View.VISIBLE);
                searchVBuscar.setIconifiedByDefault(true);
                //searchVBuscar.setFocusable(true);
                searchVBuscar.setIconified(false);
                //searchVBuscar.requestFocusFromTouch();
                btn_buscar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrarLugares(newText);
        return false;
    }

    private void mscCOnsole(String s) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> "+s);
    }
}