package foodrequest.edu.ifsp.bri.foodrequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregaLista();
    }

    public void carregaLista(){
        final ListView lista = (ListView) findViewById(R.id.listViewPedidos);

        final ArrayList<String> equpes = preencheDados();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, equpes);

        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent i = new Intent(ClassName.this,CourtDetailActivity.class);
                //startActivity(i);
                Toast.makeText(getApplicationContext(), "Pedido:" + equpes.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void  onResume(Bundle saveInstanceState){
        carregaLista();
        Toast.makeText(getApplicationContext(), "on resume!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            //Fazer o que pretende quando retorna do Voice
        }
        if (requestCode == 0 && resultCode == RESULT_OK) {
            //Fazer o que pretende quando retorna do Bluetooth
        }

        carregaLista();
        Toast.makeText(getApplicationContext(), "on result!", Toast.LENGTH_LONG).show();
    }

    private ArrayList<String> preencheDados(){
        ArrayList<Pedido> dados = new ArrayList<Pedido>();
        ArrayList<String> arrayRetorno = new ArrayList<String>();

        DBController crud = new DBController(getBaseContext());

        Cursor cursor;
        dados = crud.listAllPedidos();

        if (dados.size() > 0){
            for (Pedido pedido : dados) {
                String idPed = String.valueOf(pedido.getIdPedido());
                String precoProduto = String.valueOf(pedido.getPreco());
                String qtde = String.valueOf(pedido.getQuantidade());
                String ttl = String.valueOf(pedido.getTotal());
                String mes = String.valueOf(pedido.getMesa());

                String stringPedido = "Ped: " + idPed + " / Preço: " + precoProduto + " Qtde: " + qtde + "Total: " + ttl;

                arrayRetorno.add(stringPedido);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nenhum Pedido foi encontrado!", Toast.LENGTH_LONG).show();
        }

        return arrayRetorno;
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
        if (id == R.id.action_cadastra_produtos){
            startActivity(new Intent(MainActivity.this, CadastroProdutoActivity.class));
        }else if (id == R.id.action_cadastra_pedido){
            startActivity(new Intent(MainActivity.this, CadastroPedidoActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}