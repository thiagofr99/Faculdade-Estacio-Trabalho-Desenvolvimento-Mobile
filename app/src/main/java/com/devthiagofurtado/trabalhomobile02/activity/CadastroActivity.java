package com.devthiagofurtado.trabalhomobile02.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devthiagofurtado.trabalhomobile02.R;
import com.devthiagofurtado.trabalhomobile02.config.ConfiguracaoFirebase;
import com.devthiagofurtado.trabalhomobile02.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private EditText editNome, editTelefone, editSenha, editEmail;
    private FirebaseAuth autenticar = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfiguracaoFirebase.getDatabaseReference();
    public static String DATABASE_USUARIO = "usuario";
    private Integer cod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editTextLoginEmail);
        editSenha = findViewById(R.id.editTextSenha);
        editTelefone = findViewById(R.id.editTextTelefone);


    }

    public void cadastrar(View view) {
        if (verificarCampos()){
            //Cadastro Usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(editEmail.getText().toString());
            usuario.setNome(editNome.getText().toString());
            usuario.setSenha(editSenha.getText().toString());
            usuario.setTelefone(editTelefone.getText().toString());


            //Cadastro de Usuario
            autenticar.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        //Logar usuario logado
                        autenticar.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha());

                        //Busca o UID do usuario logado
                        String uid = autenticar.getCurrentUser().getUid();

                        usuario.salvar(uid);

                        Toast.makeText(CadastroActivity.this, "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroActivity.this,MainActivity.class));
                    } else {
                        String excessao = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            excessao = "Digite uma senha mais forte!";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            excessao = "Digite um e-mail válido!";
                        } catch (FirebaseAuthUserCollisionException e) {
                            excessao = "Esta conta já foi cadastrada";
                        } catch (Exception e) {
                            excessao = "Erro a cadastrar usuário: " + e.getMessage();
                            e.printStackTrace();
                        }


                        Toast.makeText(CadastroActivity.this, excessao, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public boolean verificarCampos() {
        if (!editNome.getText().toString().isEmpty()) {
            if (!editTelefone.getText().toString().isEmpty()) {
                if (!editSenha.getText().toString().isEmpty()) {
                    if (!editEmail.getText().toString().isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(this, "Preencha o campo Email", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Preencha o campo Senha", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } else {
                Toast.makeText(this, "Preencha o campo Telefone", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Preencha o campo Nome", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}