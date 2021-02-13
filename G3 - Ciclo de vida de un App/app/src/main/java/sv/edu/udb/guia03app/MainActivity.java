package sv.edu.udb.guia03app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new MyLifeCycleObserver());
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        btn1 = (Button) findViewById(R.id.btn1);

    }

    //Para el boton
    public void agregar(View view){
        int valor = Integer.parseInt(tv1.getText().toString());
        if(valor == 9){
            valor = -1;
        }
        valor = valor + 1;
        tv1.setText(""+valor);
    }
}