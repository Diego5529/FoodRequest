package foodrequest.edu.ifsp.bri.foodrequest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroPedidoActivity extends AppCompatActivity {

    // Cadastro de Pedidos (id, id_prod, quantidade, preço, total, mesa) – com inclusão, consulta
    EditText edtIDPedido;
    EditText edtIDProduto;
    EditText edtIDNomeProduto;
    EditText edtQuantidade;
    EditText edtPrecoProduto;
    TextView txtTotal;
    EditText edtMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtIDPedido = (EditText) findViewById(R.id.editTextIDPedido);
        edtIDProduto = (EditText) findViewById(R.id.editTextIDProduto);
        edtIDNomeProduto = (EditText) findViewById(R.id.editTextNomeProduto);
        edtPrecoProduto = (EditText) findViewById(R.id.editTextPrecoProduto);
        edtQuantidade = (EditText) findViewById(R.id.editTextQuantidade);
        edtMesa = (EditText) findViewById(R.id.editTextMesa);
        txtTotal = (TextView) findViewById(R.id.textViewTotal);

        if (getIntent().getExtras() != null){
            setDadosProdutoCarregado(getIntent().getExtras());
        }
    }

    public void setDadosProdutoCarregado(Bundle b)
    {
        int idProduto = 0;

        try {
            idProduto = b.getInt("id_produto");
        } catch (NumberFormatException e) {
        }

        if (idProduto > 0) {

            DBController crud = new DBController(getBaseContext());

            Cursor cursor;
            cursor = crud.carregaDadoByIdProduto(idProduto);

            if (cursor != null) {
                String nome = cursor.getString(1);
                String preco = cursor.getString(2);
                String id = String.valueOf(idProduto);
                edtIDProduto.setText(id);
                edtIDNomeProduto.setText(nome);
                edtPrecoProduto.setText(preco);
            } else {
                Toast.makeText(getApplicationContext(), "Produto não encontrado!", Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
        }
    }

    public void cadastrarPedido(View view) {
        DBController crud = new DBController(getBaseContext());

        int id = 0;
        if (edtIDProduto.getText().length() > 0)
            id = Integer.parseInt(edtIDProduto.getText().toString());

        String nome = "";
        if (edtIDNomeProduto.getText().length() > 0)
            nome = edtIDNomeProduto.getText().toString();

        String preco = "";
        if (edtPrecoProduto.getText().length() > 0)
            preco = edtPrecoProduto.getText().toString();

        int quantidade = 0;
        if (edtQuantidade.getText().length() > 0)
            quantidade = Integer.parseInt(edtQuantidade.getText().toString());

        int mesa = 0;
        if (edtMesa.getText().length() > 0)
            mesa = Integer.parseInt(edtMesa.getText().toString());

        if (id > 0 && nome.length() > 0 && preco.length() > 0 && quantidade > 0 && mesa > 0) {

            double precoDouble = 0;
            try {
                precoDouble = Double.parseDouble(preco); // Make use of autoboxing.  It's also easier to read.
            } catch (NumberFormatException e) {
                // p did not contain a valid double
            }

            double total = calculaTotal(quantidade, precoDouble);

            String resultado;
            resultado = crud.insereDadoPedido(id, quantidade, precoDouble, total, mesa);
            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
            limparCampos();
        }else {
            Toast.makeText(getApplicationContext(), "Preencha corretamente os campos do produto e dados do pedido antes de gravar!", Toast.LENGTH_LONG).show();
        }
    }

    public double calculaTotal(int quantidade, double preco)
    {
        return quantidade * preco;
    }

    public void consultaPedido(View view){
        DBController crud = new DBController(getBaseContext());

        int id = 0;
        if (edtIDPedido.getText().length() > 0)
            id = Integer.parseInt(edtIDPedido.getText().toString());

        if (id > 0){
            Cursor cursor;
            cursor = crud.carregaDadoByIdPedido(id);

            if (cursor != null) {
                int idPedido = cursor.getInt(0);
                int idProduto = cursor.getInt(1);
                int quantidade = cursor.getInt(2);
                double precoPedido = cursor.getDouble(3);
                double total = cursor.getDouble(4);
                int mesa = cursor.getInt(5);

                edtIDPedido.setText(String.valueOf(idPedido));
                edtIDProduto.setText(String.valueOf(idProduto));
                edtPrecoProduto.setText(String.format("%.2f", precoPedido));
                edtQuantidade.setText(String.valueOf(quantidade));
                txtTotal.setText(String.format("R$ %.2f", total));
                edtMesa.setText(String.valueOf(mesa));
            } else {
                Toast.makeText(getApplicationContext(), "Pedido não encontrado!", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Digite o ID do Pedido para ser encontrado!", Toast.LENGTH_LONG).show();
        }
    }

    public void limparCampos() {
        edtIDProduto.setText("");
        edtIDNomeProduto.setText("");
        edtPrecoProduto.setText("");
        edtQuantidade.setText("");
        edtMesa.setText("");
        txtTotal.setText("R$ 0,00.");
    }
}
