/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import core.pl.Config;
import pe.org.incn.helpers.Modules;

/**
 *
 * @author efloresp
 */
public class Connection {

    protected ServerSocket serverSocket;

    protected final int port = 9005;

    protected Socket connection;

    protected ServerSocket getServerConnection() throws IOException {
        return new ServerSocket(this.port);
    }

    public void open() throws IOException {
        System.out.println("run");

        serverSocket = this.getServerConnection();

        while (true) {
            System.out.println("init server");

            connection = serverSocket.accept();

            System.out.println("connect");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

            String json;

            System.err.println("line");
            
            while ((json  = in.readLine()) != null) {

                JSONObject obj = new JSONObject(json).getJSONObject(Config.nodeObjectName);

                System.out.println(json);

                String key = obj.getString("class");

                Modules modules = new Modules(obj);

                if (!modules.has(key)) {

                }

                out.write(modules.get(key).response() + "\n");
                out.flush();
            }

            out.close();
            in.close();
            connection.close();
        }

    }

}
