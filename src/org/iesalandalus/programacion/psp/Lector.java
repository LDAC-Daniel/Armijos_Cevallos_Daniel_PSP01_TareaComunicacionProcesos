package org.iesalandalus.programacion.psp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.iesalandalus.programacion.utilidades.Entrada;

public class Lector {

    public static void main(String[] args) {
        int numero;
        System.out.print("Dame un número y te mostraré los 10 siguientes: ");
        numero = Entrada.entero();

        try {
            // Se crea el proceso hijo Escritor usando el mismo classpath que el proceso actual
            ProcessBuilder constructor = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"),
                    "org.iesalandalus.programacion.psp.Escritor");
            Process procesoEscritor = constructor.start();

            // Se envía el número al proceso Escritor por su entrada estándar
            BufferedWriter entradaEscritor = new BufferedWriter(new OutputStreamWriter(procesoEscritor.getOutputStream()));
            entradaEscritor.write(String.valueOf(numero));
            entradaEscritor.newLine();
            entradaEscritor.flush();
            entradaEscritor.close();

            // Se muestran por consola los números que escribe el proceso Escritor
            BufferedReader salidaEscritor = new BufferedReader(new InputStreamReader(procesoEscritor.getInputStream()));
            String linea;
            while ((linea = salidaEscritor.readLine()) != null) {
                System.out.println(linea);
            }
            salidaEscritor.close();

            // Se muestran los posibles errores del proceso Escritor
            BufferedReader erroresEscritor = new BufferedReader(new InputStreamReader(procesoEscritor.getErrorStream()));
            while ((linea = erroresEscritor.readLine()) != null) {
                System.out.println("ERROR: " + linea);
            }
            erroresEscritor.close();

            procesoEscritor.waitFor();
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("La ejecución ha sido interrumpida.");
        }
    }
}