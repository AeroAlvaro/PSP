package ejercicio2_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private static final int PORT = 6001;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Servidor UDP iniciado en el puerto: " + PORT);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);
                System.out.println("Solicitud recibida de: " + packet.getAddress());

                new Thread(new PrimeHandler(socket, packet)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PrimeHandler implements Runnable {
        private DatagramSocket socket;
        private DatagramPacket requestPacket;

        public PrimeHandler(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.requestPacket = packet;
        }

        @Override
        public void run() {
            try {
                String input = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
                System.out.println("Procesando número: " + input);

                String respuesta;
                try {
                    int numero = Integer.parseInt(input);

                    if (numero < 2) {
                        respuesta = "No hay primos en este rango";
                    } else {
                        respuesta = calcularPrimos(numero);
                    }

                } catch (NumberFormatException e) {
                    respuesta = "Error: Debes enviar un número entero válido";
                }

                byte[] responseData = respuesta.getBytes();
                InetAddress clientAddress = requestPacket.getAddress();
                int clientPort = requestPacket.getPort();

                DatagramPacket responsePacket = new DatagramPacket(
                        responseData,
                        responseData.length,
                        clientAddress,
                        clientPort
                );

                socket.send(responsePacket);
                System.out.println("Respuesta enviada a " + clientAddress);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String calcularPrimos(int limite) {
            StringBuilder sb = new StringBuilder();
            boolean primero = true;

            for (int i = 2; i <= limite; i++) {
                if (esPrimo(i)) {
                    if (!primero) {
                        sb.append(",");
                    }
                    sb.append(i);
                    primero = false;
                }
            }
            return sb.toString();
        }

        private boolean esPrimo(int n) {
            if (n <= 1) return false;
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) return false;
            }
            return true;
        }
    }
}