package sv.edu.udb.guia06app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnFrame, btnLinear, btnRelative, btnTable, btnGrid, btnLoginRelative, btnLoginConstraint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FRAME
        btnFrame = (Button)findViewById(R.id.frame_layout);
        btnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setContentView(R.layout.ejemplo_frame_layout);
            }
        });

        //LINEAR
        btnLinear = (Button)findViewById(R.id.linear_layout);
        btnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.ejemplo_linear_layout);
            }
        });

        //RELATIVE
        btnRelative = (Button)findViewById(R.id.relative_layout);
        btnRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.ejemplo_relative_layout);
            }
        });

        //TABLE
        btnTable = (Button)findViewById(R.id.table_layout);
        btnTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.ejemplo_table_layout);
            }
        });

        //GRID
        btnGrid = (Button)findViewById(R.id.grid_layout);
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.ejemplo_grid_layout);
            }
        });

        //LOGIN RELATIVE
        btnLoginRelative = (Button)findViewById(R.id.login_relative_layout);
        btnLoginRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.login_relative_layout);
            }
        });

        //LOGIN CONSTRAINT
        btnLoginConstraint = (Button)findViewById(R.id.login_constraint_layout);
        btnLoginConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.login_constraint_layout);
            }
        });

    }
}