package client;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rafael on 15/01/2017.
 */

public class WebClient {

    public String enviandoJson(String json) throws Exception {

        URL url = new URL("https://www.caelum.com.br/mobile");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("content-type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        PrintStream output = new PrintStream(connection.getOutputStream());
        output.println(json);

        connection.connect();

        Scanner scanner = new Scanner(connection.getInputStream());
        String resposta = scanner.next();
        return resposta;
    }
}
