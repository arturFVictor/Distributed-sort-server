/*
ARTUR FILARDI VICTORIANO
LUCCA DE OLIVEIRA FILIZZOLA
INF3A
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static List<List<Integer>> list = null;
    private static List<Integer> tempList = null;
    private static int port = 2222;
    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private static ObjectOutputStream objectOutputStream = null;

    public static void RequisitionsHandler() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Ready...");

        try {
            socket = serverSocket.accept();
            System.out.println("Connected to " + socket.getInetAddress());

            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            if (dataInputStream.readBoolean()) {
                list = (List<List<Integer>>) objectInputStream.readObject();

                for (int i = 0; i < list.size(); i++) {
                    tempList = list.get(i);

                    for (int n = 0; n < tempList.size(); n++) {
                        Collections.sort(tempList);
                    }
                    list.set(i, tempList);
                }

                objectOutputStream.writeObject(list);

                socket.close();
                serverSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
