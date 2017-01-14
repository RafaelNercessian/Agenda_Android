package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Rafael on 14/01/2017.
 */

public class AlunosAdapter extends ArrayAdapter<Aluno> {

    private final Context context;
    private final List<Aluno> alunos;

    public AlunosAdapter(Context context, int resource, List<Aluno> alunos) {
        super(context, resource, alunos);
        this.context = context;
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=convertView;
        if(view==null){
           view = inflater.inflate(R.layout.layout_item, null);
        }
        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        if (aluno.getCaminhoFoto() != null) {
            String caminhoFoto=aluno.getCaminhoFoto();
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            foto.setImageBitmap(Bitmap.createBitmap(bitmapReduzido));
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return view;
    }
}
