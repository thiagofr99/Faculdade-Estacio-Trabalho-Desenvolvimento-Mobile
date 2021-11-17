package com.devthiagofurtado.trabalhomobile02.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devthiagofurtado.trabalhomobile02.R;
import com.devthiagofurtado.trabalhomobile02.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private EditText editLogin, editSenha;
    private FirebaseAuth usuarioReference = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLogin = findViewById(R.id.editTextLogin);
        editSenha = findViewById(R.id.editLoginSenha);
    }

    public void abrirCadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        if(verificarCampos()){
            Usuario usuario = new Usuario();
            usuario.setEmail(editLogin.getText().toString());
            usuario.setSenha(editSenha.getText().toString());

            //Logar usuario logado
            usuarioReference.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        finish();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Toast.makeText(LoginActivity.this,"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();
                    } else {

                        String excessao = "";
                        try{
                            throw task.getException();
                        }
                        catch (FirebaseAuthInvalidCredentialsException e){
                            excessao = "Email ou senha não correspondem a um usuário cadastrado.";
                        }
                        catch (FirebaseAuthInvalidUserException e){
                            excessao = "Email não possui cadastro.";
                        }
                        catch ( Exception e){
                            excessao="Erro a efetuar login: "+ e.getMessage();
                            e.printStackTrace();
                        }



                        Toast.makeText(LoginActivity.this,excessao,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public boolean verificarCampos(){
        if(!editLogin.getText().toString().isEmpty()){
            if(!editSenha.getText().toString().isEmpty()){
                return true;
            } else {
                Toast.makeText(this, "Preencha o campo Senha", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Preencha o campo Login", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}