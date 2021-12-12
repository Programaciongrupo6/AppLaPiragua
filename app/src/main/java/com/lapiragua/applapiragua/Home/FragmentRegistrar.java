package com.lapiragua.applapiragua.Home;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lapiragua.applapiragua.R;
import com.lapiragua.applapiragua.funciones.ValidacionCampos;
import com.lapiragua.applapiragua.model.FirebaseReference;
import com.lapiragua.applapiragua.model.LugarPatrimonial;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class FragmentRegistrar extends Fragment {

    // TODO: Rename and change types of parameters
    private EditText ET_nombreLugar;
    private Spinner S_tipoPatrimonio;
    private EditText ET_palabrasClave;
    private TextView TE_etiqueta;
    private EditText ET_ubicacion;
    private EditText ET_descripcion;

    private Button btnSelectPhoto;
    private Button btnTomarPhoto;
    private Button btnRegistrar;
    private ValidacionCampos validar;
    private ImageView ivPhotoDriver;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    ProgressDialog progressDialog;

    private int numeroDeLugares;
    private View view;

    private Uri imaUri;
    private String pathPhoto ="";

    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registrar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        event();
        dataBase();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1888 && resultCode == Activity.RESULT_OK && data != null) {
                /*
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ivPhotoDriver.setImageBitmap(photo);

                 */

                Bitmap photo = BitmapFactory.decodeFile(pathPhoto);
                ivPhotoDriver.setImageBitmap(photo);

                /*
                Uri uri = Uri.parse(pathPhoto);
                imaUri = uri;
                ivPhotoDriver.setImageURI(imaUri);

                 */

            } else if (requestCode == 2222 && resultCode == Activity.RESULT_OK && data != null) {
                imaUri = data.getData();
                ivPhotoDriver.setImageURI(imaUri);
            }
        }
    }

    private void init() {
        ET_nombreLugar = view.findViewById(R.id.editText_nombreLugar);
        ET_palabrasClave = view.findViewById(R.id.editText_palabrasClave);
        ET_ubicacion = view.findViewById(R.id.editText_ubicacion);
        ET_descripcion = view.findViewById(R.id.editTextMultiLine_descripcion);
        TE_etiqueta = view.findViewById(R.id.textView_etiqueta);
        S_tipoPatrimonio = view.findViewById(R.id.spinner_tipoPatrimonio);
        ivPhotoDriver = view.findViewById(R.id.imageView_Photo);


        btnRegistrar = view.findViewById(R.id.btn_sendRegistro);
        btnSelectPhoto = view.findViewById(R.id.button_selectPhoto);
        btnTomarPhoto = view.findViewById(R.id.button_tomarPhoto);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.tiposP, android.R.layout.simple_list_item_1);
        S_tipoPatrimonio.setAdapter(adapter);

        validar = new ValidacionCampos();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference(FirebaseReference.DB_REFERENCE_IMG_LUGARES);
    }


    private void event() {

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2222);

            }
        });
        btnTomarPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null){
                    File photoFile = null;

                    try {
                        photoFile = crearPhotoFile();

                    }catch (Exception e){
                        msnDebug(e.toString());
                    }

                    if (photoFile != null){
                        //imaUri = FileProvider.getUriForFile(getContext(), "com.lapiragua.applapiragua", photoFile);
                        System.out.println("ok aca");
                        Uri fotoUri = FileProvider.getUriForFile(getContext(), "com.lapiragua.applapiragua.fileprovider", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                        startActivityForResult(cameraIntent, 1888);
                    }
                }

            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String etiqueta = TE_etiqueta.getText().toString();
                String nombre = ET_nombreLugar.getText().toString();
                String tipo = S_tipoPatrimonio.getSelectedItem().toString();
                String palabraClave = ET_palabrasClave.getText().toString();
                String ubicacion = ET_ubicacion.getText().toString();
                String descripcion = ET_descripcion.getText().toString();

                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {

                    String usuario = currentUser.getEmail();

                    if (validar.formRegistros(nombre, tipo, palabraClave, etiqueta, ubicacion, descripcion, imaUri, getContext())) {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Registrando datos...");
                        progressDialog.show();

                        StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "" + getNameFile());
                        UploadTask uploadTask = fileRef.putFile(imaUri);

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()){
                                    throw Objects.requireNonNull(task.getException());
                                }
                                return fileRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri downloadUri = task.getResult();
                                LugarPatrimonial lugarPatrimonial = new LugarPatrimonial(
                                        etiqueta,
                                        nombre,
                                        tipo,
                                        palabraClave,
                                        ubicacion,
                                        descripcion,
                                        imaUri.toString(),
                                        downloadUri.toString(),
                                        usuario);
                                db.collection(FirebaseReference.DB_REFERENCE_LUGARES)
                                        .document(etiqueta)
                                        .set(lugarPatrimonial)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                msn("Lugar agregado correctamente");
                                                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                TE_etiqueta.setText("");
                                                ET_nombreLugar.setText("");
                                                ET_palabrasClave.setText("");
                                                ET_ubicacion.setText("");
                                                ET_descripcion.setText("");


                                                ivPhotoDriver.setImageDrawable(getResources().getDrawable(R.drawable.ic_outline_add_photo_alternate_24));
                                                dataBase();
                                                if (progressDialog.isShowing())
                                                    progressDialog.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error adding document", e);
                                                msn("Error al agregar el lugar");
                                            }
                                        });
                            }
                        });






                    }
                }
            }
        });

        ET_nombreLugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameWrite = ET_nombreLugar.getText().toString();
                String tipoSelect = S_tipoPatrimonio.getSelectedItem().toString();
                if (nameWrite.length() > 2) {
                    String ponerEtiqueta = validar.generadorEtiqueta(nameWrite, tipoSelect, numeroDeLugares + 1);
                    TE_etiqueta.setText(ponerEtiqueta);
                } else {
                    TE_etiqueta.setText("");
                }
            }
        });

        S_tipoPatrimonio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nameWrite = ET_nombreLugar.getText().toString();
                String tipoSelect = S_tipoPatrimonio.getSelectedItem().toString();
                if (nameWrite.length() > 2) {
                    String ponerEtiqueta = validar.generadorEtiqueta(nameWrite, tipoSelect, numeroDeLugares + 1);
                    TE_etiqueta.setText(ponerEtiqueta);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private File crearPhotoFile() throws IOException {

        //CREA UN ARCHIVO TEMPORAL DE LA IMAGEN

        String nameTime = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        String imgNameFile = "imagen_"+nameTime;
        File storageFile = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
                imgNameFile,
                ".jpg",
                storageFile
        );
        pathPhoto = photoFile.getAbsolutePath();
        return photoFile;
    }


    private void dataBase() {
        db.collection(FirebaseReference.DB_REFERENCE_LUGARES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            numeroDeLugares = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                numeroDeLugares += 1;
                            }

                            //msn(String.format("Numero de lugares: %s",numeroDeLugares));
                        } else {
                            msn("Error al obtener el lugar");
                        }
                    }
                });
    }


    private String getNameFile() {
        int a = (int) (Math.random() * 10 + 1);
        int b = (int) (Math.random() * 10 + 1);
        int c = (int) (Math.random() * 10 + 1);
        int d = (int) (Math.random() * 10 + 1);
        int num1 = (int) (Math.random() * 1012 + 2111);
        int num2 = (int) (Math.random() * 1012 + 2111);
        String[] element = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        String nameRandon = "driver_" +
                element[a] + num1 + element[b] +
                "_" +
                element[c] + num2 + element[d] + "comp.jpg";
        return nameRandon;
    }







    private void showHomeUI() {
        Intent intent = new Intent(getContext(), HomeAppActivity.class);
        startActivity(intent);
    }

    private void msn(String data) {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
    }

    private void msnDebug(String s) {
        Log.d("APP-DEBUG", s);
    }

}