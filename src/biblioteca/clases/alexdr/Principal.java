package biblioteca.clases.alexdr;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) {
		// Variable que recoge la decision del usuario para salir o continuar en el programa. Solo cambiara en caso de que elija cerrar el programa
		boolean decision = false; 
		int eleccion; // Variable que  almacena la decision que quiera realizar el usuario
		Biblioteca biblioteca = new Biblioteca("Armazon");
		do  {
			Scanner sc = new Scanner(System.in);
			eleccion=0; //Inicializacion de la variable por cada iteracion del bucle
			System.out.println("\n-------- MENU PRINCIPAL --------\n" 
					+ "\n1. Abrir Menu Gestion Reservas.\n" 
					+ "\n2. Cerrar Programa.\n"
					+ "\n------------------------------------------");
			
			try {
				eleccion = sc.nextInt(); 
			} catch (InputMismatchException e) {
				System.out.println("\nHas introducido un valor no valido.\n");
			}
			switch (eleccion) {
				case 1:
					sc.nextLine();
					biblioteca.mostrarListadoLibros();
					biblioteca.mostrarListadoSocios();
					biblioteca.menu(); 
					
					break;
				case 2:
					decision = true;
					break;
				default:
					System.out.println("Introduce [1,2]");
					break;
			}
 
		}while(!decision);
		System.out.println("\n-------------------- HAS CERRADO EL PROGRAMA --------------------\n");          
		
	}
}

