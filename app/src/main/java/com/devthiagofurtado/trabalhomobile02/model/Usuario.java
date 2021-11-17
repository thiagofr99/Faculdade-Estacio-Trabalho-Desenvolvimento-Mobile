package com.devthiagofurtado.trabalhomobile02.model;

import com.devthiagofurtado.trabalhomobile02.activity.CadastroActivity;
import com.devthiagofurtado.trabalhomobile02.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    private String email;


    private String senha;

    private String nome;

    private String telefone;

    public void salvar(String uidAut){
        Task<Void> reference = ConfiguracaoFirebase.getDatabaseReference().child(CadastroActivity.DATABASE_USUARIO).child(uidAut).setValue(this);

    }

    @Exclude
    public String getSenha() {
        return senha;
    }


}
