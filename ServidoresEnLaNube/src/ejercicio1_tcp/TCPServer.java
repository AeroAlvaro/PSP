package ejercicio1_tcp;

import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPServer {
    private static final int PORT = 6000;

    public static void main(String[] args) {
        System.out.println("Servidor TCP iniciado en el puerto: " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private int numeroSecreto;
        private Random random;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.random = new Random();
            generarNuevoNumero();
        }

        private void generarNuevoNumero() {
            this.numeroSecreto = random.nextInt(100) + 1;
            System.out.println("Nuevo juego para cliente: " + clientSocket.getInetAddress() + ". Número secreto: " + numeroSecreto);
        }

        @Override
        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String inputLine;
                out.println("Adivina el número entre 1 y 100");

                while ((inputLine = in.readLine()) != null) {
                    try {
                        int intento = Integer.parseInt(inputLine);

                        if (intento < numeroSecreto) {
                            out.println("El número es mayor");
                        } else if (intento > numeroSecreto) {
                            out.println("El número es menor");
                        } else {
                            out.println("Correcto! Has adivinado el número");
                            out.println("¿Quieres jugar otra vez? (s/n)");

                            String respuesta = in.readLine();
                            if (respuesta != null && respuesta.equalsIgnoreCase("s")) {
                                generarNuevoNumero();
                                out.println("Nuevo número generado");
                            } else {
                                out.println("Adiós");
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        out.println("Por favor, introduce un número válido");
                    }
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado");
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}