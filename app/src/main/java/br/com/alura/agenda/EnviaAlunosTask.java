package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunosJson;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;
import client.WebClient;

/**
 * Created by Rafael on 15/01/2017.
 */

public class EnviaAlunosTask extends AsyncTask {

    private final Context context;
    private String respostaJson;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Enviando alunos para servidor!");
        dialog.setIndeterminate(true);
        dialog.setProgress(0);
        dialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            AlunoDAO dao = new AlunoDAO(context);
            List<Aluno> alunos = dao.listaAlunosBanco();
            AlunosJson alunosJson = new AlunosJson();
            String json = alunosJson.converteParaJson(alunos);
            WebClient client = new WebClient();
            respostaJson = client.enviandoJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respostaJson;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        dialog.dismiss();
        Toast.makeText(context,(String) o,Toast.LENGTH_LONG).show();
    }
}
