package org.iesalandalus.programacion.psp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Tuberia {
    public static void main(String[] args) {
        try {
            // Se crean los dos procesos hijo, cada uno con su comando y sus argumentos
            Process primerProceso = new ProcessBuilder("CMD", "/C", "DIR").start();
            Process segundoProceso = new ProcessBuilder("CMD", "/C", "SORT").start();

            // Salida del primer proceso y entrada del segundo proceso
            BufferedReader lectorPrimero = new BufferedReader(new InputStreamReader(primerProceso.getInputStream()));
            BufferedWriter escritorSegundo = new BufferedWriter(new OutputStreamWriter(segundoProceso.getOutputStream()));

            // Se pasa cada línea de la salida del primero a la entrada del segundo
            String linea;
            while ((linea = lectorPrimero.readLine()) != null) {
                escritorSegundo.write(linea);
                escritorSegundo.newLine();
            }

            // Se cierran los flujos para que el segundo proceso sepa que ya no hay más datos
            escritorSegundo.close();
            lectorPrimero.close();

            // Se muestra por pantalla el resultado generado por el segundo proceso
            BufferedReader lectorSegundo = new BufferedReader(new InputStreamReader(segundoProceso.getInputStream()));
            while ((linea = lectorSegundo.readLine()) != null) {
                System.out.println(linea);
            }
            lectorSegundo.close();

            // Se espera a que los dos procesos terminen
            primerProceso.waitFor();
            segundoProceso.waitFor();

            System.out.println("Finalizada la ejecución");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("La ejecución ha sido interrumpida.");
        }
    }
}