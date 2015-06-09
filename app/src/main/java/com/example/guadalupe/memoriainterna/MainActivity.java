package com.example.guadalupe.memoriainterna;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{

    private EditText txtTexto;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTexto = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnAbrir = (Button)findViewById(R.id.btnAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {

        if (v.equals(btnGuardar)){
            String str = txtTexto.getText().toString();

            //Clase que permite grabar texto en un archivo
            FileOutputStream fout=null;
            try {
                //Metodo que escribe y abre un archivo con un nombre especifica
                //La constante MODE_WORLD_READABLE indica que este arvhivo lo puede
                //leer cualquier apllicacion
                fout = openFileOutput("archivoTexto.txt", MODE_WORLD_READABLE);

                //Convierte un stream de caracteres en un stream de bytes
                OutputStreamWriter ows = new OutputStreamWriter(fout);
                ows.write(str); //Escribe en el buffer la cadena de texto
                ows.flush(); //Volca lo que hay en el buffer al archivo
                ows.close(); //Cierra el archivo de texto

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Toast.makeText(getBaseContext(),"El archivo se ha almacenado!!!", Toast.LENGTH_SHORT).show();
            txtTexto.setText("");
        }

        if (v.equals(btnAbrir)){
            try {

                //Se lee el archivo de texto indicado
                FileInputStream fin = openFileInput("archivoTexto.txt");
                InputStreamReader isr = new InputStreamReader(fin);

                char[] inputBuffer = new char[READ_BLOCK_SIZE	];
                String str= "";

                //Se lee el archivo de texto mientras no se llegue al final de él
                int charRead;
                while ((charRead=isr.read(inputBuffer))>0) {
                    //Se lee por bloques de 100 caracteres
                    //ya que se desconoce el tamaño del texto
                    //Y se va copiando a una cadena de texto
                    String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                    str += strRead;

                    inputBuffer = new char [READ_BLOCK_SIZE];
                }

                //Se muestra el texto leido en la caje de texto
                txtTexto.setText(str);

                isr.close();

                Toast.makeText(getBaseContext(),"El archivo ha sido cargado", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    }
}