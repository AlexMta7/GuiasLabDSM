package sv.edu.udb.guia9_analisis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText txView;
    private Button btnBuscar;

    String repo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_item);
        txView = (EditText) findViewById(R.id.tvRepo);
        btnBuscar = (Button) findViewById(R.id.btn_Buscar);



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txView.getText().toString().isEmpty()){
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("https://api.github.com")
                            .addConverterFactory(GsonConverterFactory.create());
                    try {
                        Retrofit retrofit = builder.build();
                        GitHubClient client = retrofit.create(GitHubClient.class);
                        Call<List<GitHubRepo>> call = client.reposForuser(txView.getText().toString());
                        call.enqueue(new Callback<List<GitHubRepo>>() {
                            @Override
                            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                                List<GitHubRepo> repos = response.body();
                                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, -1, repos));
                            }

                            @Override
                            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Coloca el nombre del repositorio", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Coloca el nombre del repositorio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
