package org.iesalandalus.programacion.psp;

import org.iesalandalus.programacion.utilidades.Entrada;

public class Escritor {

    public static void main(String[] args) {
        int numero;

        // Se lee por la entrada estándar el número enviado por el proceso Lector
        numero = Entrada.entero();

        // Se escriben por la salida estándar los 10 números siguientes
        for (int i = 1; i <= 10; i++) {
            System.out.println(numero + i);
        }
    }
}