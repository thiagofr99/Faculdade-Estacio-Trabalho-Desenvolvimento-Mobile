package com.devthiagofurtado.trabalhomobile02.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devthiagofurtado.trabalhomobile02.R;
import com.devthiagofurtado.trabalhomobile02.api.NoticesActivity;
import com.devthiagofurtado.trabalhomobile02.config.ConfiguracaoFirebase;
import com.devthiagofurtado.trabalhomobile02.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ValueEventListener valueEventListenerUsuario;
    private FirebaseAuth usuarioReference = FirebaseAuth.getInstance();
    private TextView usuarioLogado;
    private ImageView imagePerfil;
    private Button buttonLogar, buttonSair;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    private DatabaseReference usuarioEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioLogado = findViewById(R.id.textLogado);
        imagePerfil = findViewById(R.id.imageLogado);
        buttonLogar = findViewById(R.id.buttonLogar);
        buttonSair = findViewById(R.id.buttonSair);

        verificarUsuarioLogado();
    }

    public void minhaConta(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void noticiasGerais(View view) {
        startActivity(new Intent(this, NoticesActivity.class).putExtra("tipo","general"));
    }
    public void noticiasTecnologia(View view) {
        startActivity(new Intent(this, NoticesActivity.class).putExtra("tipo","technology"));
    }
    public void noticiasEntreterimento(View view) {
        startActivity(new Intent(this, NoticesActivity.class).putExtra("tipo","entertainment"));
    }
    public void noticiasSaude(View view) {
        startActivity(new Intent(this, NoticesActivity.class).putExtra("tipo","health"));
    }
    public void noticiasEsporte(View view) {
        startActivity(new Intent(this, NoticesActivity.class).putExtra("tipo","sports"));
    }

    public void noticiasNegocios(View view) {
        startActivity(new Intent(this, NoticesActivity.class).putExtra("tipo","business"));
    }

    public void verificarUsuarioLogado() {
        if (usuarioReference.getCurrentUser() != null) {
            imagePerfil.setVisibility(View.VISIBLE);
            usuarioLogado.setVisibility(View.VISIBLE);
            buttonSair.setVisibility(View.VISIBLE);
            buttonSair.setEnabled(true);
            buttonLogar.setVisibility(View.INVISIBLE);
            buttonLogar.setEnabled(false);

        }
    }

    public void deslogarUsuario(View view) {
        usuarioReference.signOut();
        imagePerfil.setVisibility(View.INVISIBLE);
        usuarioLogado.setVisibility(View.INVISIBLE);
        buttonSair.setVisibility(View.INVISIBLE);
        buttonSair.setEnabled(false);
        buttonLogar.setVisibility(View.VISIBLE);
        buttonLogar.setEnabled(true);
        recreate();
    }

    public void recuperarDadosUsuario() {
        if (usuarioReference.getCurrentUser() != null){
            String uid = usuarioReference.getCurrentUser().getUid();
            usuarioEnd = reference.child(CadastroActivity.DATABASE_USUARIO).child(uid);

            valueEventListenerUsuario = usuarioEnd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    usuarioLogado.setText(usuario.getNome());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosUsuario();
    }
}