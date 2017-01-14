package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by Rafael on 14/01/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage mensagem = SmsMessage.createFromPdu((byte[]) pdus[0]);
        String telefone = mensagem.getDisplayOriginatingAddress();
        AlunoDAO dao=new AlunoDAO(context);
        if(dao.ehAluno(telefone)){
            Toast.makeText(context,"Chegou um SMS",Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

    }
}
