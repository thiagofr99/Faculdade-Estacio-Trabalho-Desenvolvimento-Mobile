package com.devthiagofurtado.trabalhomobile02.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devthiagofurtado.trabalhomobile02.R;
import com.devthiagofurtado.trabalhomobile02.config.ConfiguracaoFirebase;
import com.devthiagofurtado.trabalhomobile02.model.Noticia;
import com.devthiagofurtado.trabalhomobile02.model.Postagem;
import com.google.firebase.database.DatabaseReference;

public class NovaNoticiaActivity extends AppCompatActivity {
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    public static String DATABASE_USUARIO = "noticias";
    private TextView textNome;
    private EditText editNoticia, editTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_noticia);

        textNome = findViewById(R.id.textAutor);
        editNoticia = findViewById(R.id.editTextNoticia);
        editTitulo = findViewById(R.id.editTextTitulo);

        String nome = getIntent().getStringExtra("nome");
        textNome.setText(nome);
    }

    public void cadastrarNoticia(View view){
        Noticia postagem = new Noticia();
        postagem.setAutor(textNome.getText().toString());
        postagem.setNoticia(editNoticia.getText().toString());
        postagem.setTitulo(editTitulo.getText().toString());

        reference.child(DATABASE_USUARIO).push().setValue(postagem);
        Toast.makeText(NovaNoticiaActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}