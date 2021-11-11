package gracilianobarroso.cursoandroid.organizze.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import gracilianobarroso.cursoandroid.organizze.R;
import gracilianobarroso.cursoandroid.organizze.config.ConfiguracaoFirebase;
import gracilianobarroso.cursoandroid.organizze.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button buttonCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editName);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if (!textoNome.isEmpty()){
                    if (!textoEmail.isEmpty()){
                        if (!textoSenha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrarUsuario();

                        }else {
                            Toast.makeText(CadastroActivity.this, "Preencha o campo senha!", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(CadastroActivity.this, "Preencha o campo email!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(CadastroActivity.this, "Preencha o campo nome!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}