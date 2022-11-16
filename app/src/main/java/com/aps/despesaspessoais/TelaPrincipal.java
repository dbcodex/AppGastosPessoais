package com.aps.despesaspessoais;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class TelaPrincipal extends AppCompatActivity {

    private TextView nomeUsuario,emailUsuario;
    private Button btDeslogar, btCadastrarDespesas;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();

        btDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new  Intent(TelaPrincipal.this,FormLogin.class);
                startActivity((intent));
                finish();
            }
        });

        btCadastrarDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, FormDespesas.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    emailUsuario.setText(email);
                }
            }
        });
//Carregar as informacoes :)
        LinearLayout listDespesas = findViewById(R.id.listDespesas);
        listDespesas.removeAllViews();

        new Thread(new Runnable(
        ) {
            //String usuarioID;
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Cadastro").build();
                CadastroDAO dao = db.cadastroDAO();

                //FirebaseFirestore dbFb = FirebaseFirestore.getInstance();
                //usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

                List<Cadastro> cadastro = dao.getByChaveFirebase(usuarioID.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String msg;

                        for (Cadastro p:cadastro){
                            TextView textViewT = new TextView(TelaPrincipal.this);
                            msg = p.nome + " - " + p.email;
                            textViewT.setText(msg);

                            listDespesas.addView(textViewT);
                        }
                    }
                });
            }
        }).start();
    }

    private void IniciarComponentes(){
        nomeUsuario = findViewById(R.id.textNomeUser);
        emailUsuario = findViewById(R.id.textEmailUser);
        btDeslogar = findViewById(R.id.btDeslogar);
        btCadastrarDespesas = findViewById(R.id.btCadastrarDespesas);
    }

    public void listDespesas(View view) {
        LinearLayout listDespesas = findViewById(R.id.listDespesas);
        listDespesas.removeAllViews();

        new Thread(new Runnable(
        ) {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Cadastro").build();
                CadastroDAO dao = db.cadastroDAO();

                List<Cadastro> cadastros = dao.getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String msg;
                        for (Cadastro p:cadastros){
                            TextView textViewT = new TextView(TelaPrincipal.this);
                            msg = p.id + " - " + p.chaveFirebase + " - " + p.nome + " - " + p.email;
                            textViewT.setText(msg);

                            listDespesas.addView(textViewT);
                        }
                    }
                });
            }
        }).start();
    }

}