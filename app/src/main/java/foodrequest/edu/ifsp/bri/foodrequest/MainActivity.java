package foodrequest.edu.ifsp.bri.foodrequest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    EditText edtID;
    EditText edtNome;
    EditText edtPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtID = (EditText) findViewById(R.id.editTextID);
        edtNome = (EditText) findViewById(R.id.editTextNome);
        edtPreco = (EditText) findViewById(R.id.editTextPreco);
    }

    public void gravarProduto(View view) {
        DBController crud = new DBController(getBaseContext());
        String nome = edtNome.getText().toString();
        String preco = edtPreco.getText().toString();
        String resultado;
        resultado = crud.insereDado(nome, preco);
        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        edtNome.setText("");
        edtPreco.setText("");
    }

    public void buscarProduto(View view) {
        DBController crud = new DBController(getBaseContext());

        int id = Integer.parseInt(edtID.getText().toString());
        Cursor cursor;
        cursor = crud.carregaDadoById(id);

        if (cursor != null) {
            String nome = cursor.getString(1); // nome
            String preco = cursor.getString(2);

            edtNome.setText(nome);
            edtPreco.setText(preco);
        } else {
            Toast.makeText(getApplicationContext(), "Produto não encontrado!", Toast.LENGTH_LONG).show();
        }
    }

    public void excluirProduto(View view) {
        DBController crud = new DBController(getBaseContext());
        int id = Integer.parseInt(edtID.getText().toString());
        crud.deletaRegistro(id);
        edtID.setText("");
        edtNome.setText("");
        edtPreco.setText("");
        Toast.makeText(getApplicationContext(), "Registro excluido!", Toast.LENGTH_LONG).show();
    }

    public void alterarProduto(View view) {
        DBController crud = new DBController(getBaseContext());
        int id = Integer.parseInt(edtID.getText().toString());
        String nome = edtNome.getText().toString();
        String preco = edtPreco.getText().toString();
        crud.alteraRegistro(id, nome, preco);
        Toast.makeText(getApplicationContext(), "Registro alterado!", Toast.LENGTH_LONG).show();
        edtID.setText("");
        edtNome.setText("");
        edtPreco.setText("");
    }

    public void limparCampos(View view) {
        edtID.setText("");
        edtNome.setText("");
        edtPreco.setText("");
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
}