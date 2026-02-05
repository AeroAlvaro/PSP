package org.example;

public class AlgoritmoPropio {
    public static String cifrarComplejo(String mensaje, String clave) {
        StringBuilder resultado = new StringBuilder();
        clave = clave.toUpperCase();
        int claveIndex = 0;

        for (int i = 0; i < mensaje.length(); i++) {
            char caracter = mensaje.charAt(i);
            if (Character.isLetter(caracter)) {
                char base = Character.isLowerCase(caracter) ? 'a' : 'A';
                int desplazamiento = clave.charAt(claveIndex % clave.length()) - 'A';
                caracter = (char) ((caracter - base + desplazamiento) % 26 + base);
                claveIndex++;
            }
            resultado.append(caracter);
        }
        return resultado.toString();
    }

    public static String descifrarComplejo(String mensaje, String clave) {
        StringBuilder resultado = new StringBuilder();
        clave = clave.toUpperCase();
        int claveIndex = 0;

        for (int i = 0; i < mensaje.length(); i++) {
            char caracter = mensaje.charAt(i);
            if (Character.isLetter(caracter)) {
                char base = Character.isLowerCase(caracter) ? 'a' : 'A';
                int desplazamiento = clave.charAt(claveIndex % clave.length()) - 'A';
                caracter = (char) ((caracter - base - desplazamiento + 26) % 26 + base);
                claveIndex++;
            }
            resultado.append(caracter);
        }
        return resultado.toString();
    }

    public static void main(String[] args) {
        String texto = "ESTO ES UN MENSAJE SECRETO";
        String clave = "JAVA";

        String cifrado = cifrarComplejo(texto, clave);
        System.out.println("Cifrado: " + cifrado);
        System.out.println("Descifrado: " + descifrarComplejo(cifrado, clave));
    }
}