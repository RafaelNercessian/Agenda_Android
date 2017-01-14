package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.AlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private List<Aluno> alunos;
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        carregaLista();
        Button botaoSalvarAluno = (Button) findViewById(R.id.lista_alunos_salvar);
        botaoSalvarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaOFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(vaiParaOFormulario);
            }
        });
        registerForContextMenu(listaAlunos);


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Aluno aluno = (Aluno) adapter.getItemAtPosition(position);
                Intent intentVaiParaOFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiParaOFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiParaOFormulario);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.listaAlunosBanco();
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
      //  ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        ArrayAdapter<Aluno> alunosAdapter = new AlunosAdapter(this, 0, alunos);
       // listaAlunos.setAdapter(adapter);
      listaAlunos.setAdapter(alunosAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = alunos.get(info.position);
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intentLigar = new Intent(Intent.ACTION_CALL);
                intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions(ListaAlunosActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);
                }else{
                    startActivity(intentLigar);
                }
                return false;
            }
        });
        
        MenuItem site = menu.add("Site");
        String siteAluno = aluno.getSite();
        if(!siteAluno.startsWith("http://")){
            siteAluno="http://"+siteAluno;
        }
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse(siteAluno));
        site.setIntent(intentSite);

        MenuItem sms = menu.add("SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+aluno.getTelefone()));
        sms.setIntent(intentSms);

        MenuItem mapa = menu.add("Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        mapa.setIntent(intentMapa);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.remover(aluno);
                dao.close();
                Toast.makeText(ListaAlunosActivity.this,"Deletar aluno "+aluno.getNome(),Toast.LENGTH_SHORT).show();
                carregaLista();
                return false;
            }
        });
    }
}
