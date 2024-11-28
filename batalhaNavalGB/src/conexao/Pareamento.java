package conexao;

import java.io.*;
import java.net.*;
import java.util.*;

public class Pareamento {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Pareamento() {
        @SuppressWarnings("unused")
		final int debugging = 0;
    }

    public void iniciaServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado na porta " + port);
        socket = serverSocket.accept();
        System.out.println("Cliente conectado");
        setupStreams();
    }

    public void conecta(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("Conectado ao servidor " + host + " na porta " + port);
        setupStreams();
    }

    private void setupStreams() throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void enviaString(String message) {
        writer.println(message);
    }

    public String recebeString() throws IOException {
        return reader.readLine();
    }

    public void fechaContato() throws IOException {
        reader.close();
        writer.close();
        socket.close();
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    public String getLocalIPv4Address() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof java.net.Inet4Address) {
                    return inetAddress.getHostAddress();
                }
            }
        }
        return InetAddress.getLocalHost().getHostAddress();
    }
}
