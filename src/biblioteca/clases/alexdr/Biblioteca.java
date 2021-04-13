package biblioteca.clases.alexdr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Biblioteca  {
	//Atributos de la clase
	private ArrayList<Socio> listaSocios;
	private ArrayList<Libro> listadoLibros;
	private ArrayList<Prestamo> listadoPrestamos;
	private String nombreBiblioteca;
	
	public Biblioteca(String nombreBiblioteca) {
		this.nombreBiblioteca = nombreBiblioteca;
		this.nombreBiblioteca = nombreBiblioteca.toUpperCase(); 
		this.listaSocios = new ArrayList<>();
		this.listadoLibros = new ArrayList<>();
		this.listadoPrestamos = new ArrayList<>();
		
	}
	
	//Metodo que ejecutara¡ el menu principal de la biblioteca.
	//Este metodo se encarga de recoger las decisiones que vaya tomando el usuario, y realizar las llmadas pertinentes a los demas metodos
	public void menu()  {
		Scanner sc = new Scanner(System.in);
		boolean salir1=false; // Variable utilizada para salir-continuar del menu de la biblioteca
		System.out.println("\n--------------------- BIENVENIDO A LA BIBLIOTECA: "+nombreBiblioteca+" ---------------------\n"); 
		do {
			int decision = 0;
			System.out.println("\n---------- MENU DE GESTION RESERVAS: "+nombreBiblioteca+" --------\n"
						+ "\n1. Reservar Libro."
						+ "\n2. Ver lista de reservas."
						+ "\n3. Alta Socio."
						+ "\n4. Alta Libro."
						+ "\n5. Informe de biblioteca."
						+ "\n6. devolver libro ."
						+ "\n7. Salir al menu principal.");	
			//Bloque que intentara almacenar la decision del usuario. Si la entrada por teclado son letras, lanzara un mensaje de error informando.	
			try {
				decision = Integer.parseInt(sc.nextLine()); //Otra manera de recoger informacion del usuario. El string entrante lo intenta convertir a integer, para despues asignarlo a la variable "decision"  
			} catch (NumberFormatException e) {
				System.out.println("\nHas introducido un valor no valido.\n");
			}	
			//Si el valor numerico introducido no esta entre los valores indicados, mostrara el siguiente mensaje.	
			if (decision<=0 || 8<decision) System.out.println("-----. Numeros validos: [1-7] .-------\n");
			//Estructura switch, que en funcion del valor de "decision" entrarÃ¡ en el correspondiente case.
			switch (decision) {
				case 1:
					try { // excepcion, mostrara¡ el mensaje que tiene el catch
						crearPrestamo(); 
						añadirPrestamoAFichero(listadoPrestamos);
					} catch (InputMismatchException e) {
						System.out.println("\nERROR: dato intruducido es incorrecto \n");
					}
					break;
				case 2:
					mostrarListadoPrestamos();
					break;
				case 3:
					crearNuevoSocio(); 
					añadirSocioAFichero(listaSocios);
					break;
				case 4:
						crearLibros(); //Llamada al metodo que permite crear libros 
						añadirLibroAFichero(listadoLibros);
						break;
				case 5:
					System.out.println(toString()); 
						break;
				case 6:
					devolverPrestamo(listadoPrestamos); 
						break;
				case 7:
					try {
						guardarFicheros();
					} catch (Exception e) {
						System.out.println("Archivo no encontrado");
					}
					salir1=true; 
					System.out.println("\n-------------------- HAS CERRADO LA BIBLIOTECA: "+nombreBiblioteca+" --------------------\n"); //Mensaje personalizado para avisar que se ha cerrado la biblioteca, (Saldra¡ al menu principal)
						break;
				}
		} while (!salir1);
	}
	//Metodo para crea prestamos en la biblioteca, verifica si el socio y el libro estan registrados, por medio de su dni(socio) y titulo(libro).
	private void crearPrestamo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Para realizar un prestamo introduce un DNI ya registrado:");
		String dni = sc.nextLine();
		System.out.println("tambien debes introducir un titulo ya registrado:");
		String titulo = sc.nextLine();
		// metiendolas directamente en el IF
		// pero lo he dejado asi para que sea mas legible
		Socio socio = comprobarDni(dni); // LLmada a metodo que comprueba si el dni esta registrado.
		Libro libro = comprobarTitulo(titulo); //LLamada a metodo que comprueba que el titulo existe.

		if (socio != null && libro != null) { 
											
			if (socio.getLibroPrestado() == null) { 
				Prestamo prestamo = new Prestamo(socio, libro); // se crea el prestamo
				listadoPrestamos.add(prestamo); //Se añade el prestamo a la lista de prestamos
				socio.setLibroPrestado(libro); 
				libro.setDisponible(false); //Se cambia la disponibilidad del libro a NO DISPONIBLE
				System.out.println("\n------------------ Prestamo creado con exito ------------------\n"); 
			} else {
				//Salida (aviso) de que el socio ya tenia un libro prestado (no se puede hacer otro prestamo).
				System.out.println("El lector con DNI: " + socio.getDni() + " no puede tener mas de un libro prestado."); 
			}
		}
	}
	//MEtodo que comprueba si el titulo pasado por parametro existe ya en la biblioteca, 
	//devolvera el libro esta libre, si no hay ninguno disponible devolvera null, al igual que si no existe ningun libro con ese titulo. 
	private Libro comprobarTitulo(String titulo) {
		boolean encontrado=false; //Variable que controla en caso de que el libro exista pero no este disponible.
		Iterator<Libro> it = listadoLibros.iterator(); //Se crea el iterador para recorrer el arraylist
		
		while (it.hasNext()) {
			Libro libro = it.next();
			if (libro.isDisponible() && libro.getTitulo().equalsIgnoreCase(titulo)) { // Condicion que evalua que le libro este disponible y los titulos sean iguales
				System.out.println("\nEl libro: " + libro.getTitulo() + " esta DISPONIBLE. Ejemplar: " + libro.getIsbn());
				return libro;
			} else if (libro.getTitulo().equalsIgnoreCase(titulo)) { //Condicion que evalua si el titulo es igual, una vez comprobado que el libro no esta disponible
				System.out.println("\nEl libro: " + libro.getTitulo() + "  NO DISPONIBLE. Ejemplar: " + libro.getIsbn());
				encontrado = true; // VAriable controla que el libro existe, por lo que NO se mostrara el mensaje de que no existe 
			}
		}
		if (!encontrado) {//Si el libro no existe en la biblioteca se avisara con el siguiente mensaje
			System.out.println("\nEl titulo: " + titulo + " no esta registrado. No se puede realizar el prestamo.");
		}
		return null; //Si llega hasta aqui, significa que no hay ningun libro con ese titulo que este disponible, o directamente que no existe.
	}
	
	//Metodo que comprueba si el DNI pasado por parametro existe (esta registrado) en la biblioteca. 
	//He utilizado el metodo completo para recorrer el array a modo de practica, pero seria mas eficiente hacerlo con for-each 
	private Socio comprobarDni(String dni) {
		Iterator<Socio> it = listaSocios.iterator(); 
		
		while (it.hasNext()) { 
			Socio socio = it.next();
			if (socio.getDni().equalsIgnoreCase(dni)) {//Condicion que comprueba si el dni del socio es igual al pasdo por parametro
				System.out.println("\nEl DNI: " + socio.getDni() + " existe y corresponde a: " + socio.getNombre()+ " " + socio.getApellidos());
				return socio; //devuelve el socio que coincide con el dni pasado por parametro
			}
		}
		System.out.println("El DNI introducido no esta registrado");
		return null; //Devolvera null en caso de que el dni no este registrado
	}

	
	private void crearLibros() throws InputMismatchException {
		Scanner sc = new Scanner(System.in);
		System.out.println("-------------- ¿Cuantos libros quieres dar de alta? --------------------");
		int numlibros = 0;
		do {
			numlibros = Integer.parseInt(sc.nextLine());
			if (numlibros <= 0) {
				System.out.println("Introduce un numero mayor a 0"); 
			}
		} while (numlibros <= 0);
		sc.nextLine(); 
		
		for (int i = 0; i < numlibros; i++) {
			System.out.println("\nIntroduce el Titulo:"); String titulo = sc.nextLine();
			System.out.println("\nIntroduce los Autor:"); String autor = sc.nextLine();
			System.out.println("\nIntroduce cuantos ejemplares: "); int ejemplares = Integer.parseInt(sc.nextLine());
			System.out.println("\nIntroduce el precio: ");float precio = Float.parseFloat(sc.nextLine());
			
			Libro libro = new Libro(listadoLibros.size() + 101, titulo, autor,ejemplares,precio); 
			listadoLibros.add(libro);
			System.out.println("\n--------------. Libro creado con exito! con ISBN: " + libro.getIsbn() + ". ------------------\n");	
		}          
	}
	
	public static void añadirLibroAFichero(ArrayList <Libro> listadoLibros) {
		FileWriter fw =null;
		BufferedWriter bw=null;
		try {
			fw = new FileWriter("C:\\Users\\picha\\eclipse-workspace\\biblioteca\\libros.csv", true);
			bw = new BufferedWriter(fw);
			for (Libro libro : listadoLibros) {
				bw.write(libro.getLibro());
			}
			bw.close();
			System.out.println("Archivo modificado ..");
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					
					fw.close();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void crearNuevoSocio() throws InputMismatchException {
		Scanner sc = new Scanner(System.in);
		System.out.println("-------------- ¿Cuantos socios quieres dar de alta? --------------------");
		int numUsers = 0;
		//Bucle que se repetira¡ mientras que el numero introducido no sea mayor que 0 
		do { 
			numUsers = sc.nextInt();
			if (numUsers <= 0) {
				System.out.println("Introduce un numero mayor a 0"); //Aviso al usuario de los valores permitidos
			}
		} while (numUsers <= 0);
		sc.nextLine();
		//Bucle que se repetira segun el numero de socio que quieren dar de alta
		for (int i = 0; i < numUsers; i++) {
			System.out.println("\nIntroduce el DNI correcto:"); 
			Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]"); //Se define el patron que debe cumplir el dni ESTO LO ESTOY APRENDIENDO DE UN CURSO ONLINE UDEMY.com
			String dni = sc.nextLine();
			Matcher real = pat.matcher(dni); //Comprobacion que guardara la variable 
			while(!real.matches()){ //Si el DNI no es real realizara el bucle
		       System.out.println("El DNI introducido es incorrecto, debes introducir un DNI correcto a continuacion:");
		        dni = sc.nextLine(); 
		        real = pat.matcher(dni); //Comprobacion
		    }
			System.out.println("\nIntroduce el Nombre:"); String nombre = sc.nextLine();
			System.out.println("\nIntroduce los Apellidos:"); String apellidos = sc.nextLine();
			System.out.println("\nIntroduce el Telefono: "); int telefono = Integer.parseInt(sc.nextLine());
			System.out.println("\nIntroduce el Email: "); String email = sc.nextLine();
			
			Socio socio = new Socio(dni, nombre, apellidos,telefono,email);
			if (!listaSocios.contains(socio)) { 
				listaSocios.add(socio); 
				System.out.println("\n-------------- socio dado de alta con exito. ------------------\n");
			} else { //Si el socio ya esta registrado, mostrara el siguiente mensaje
				System.out.println("\nEl socio con DNI: " + dni + " ya esta dado de alta, no se puede duplicar.\n");
			}
		}
	}
	
	public static void añadirSocioAFichero(ArrayList <Socio>listaSocios) {
	
		FileWriter fw =null;
		BufferedWriter bw=null;
		try {
			fw = new FileWriter("C:\\Users\\picha\\eclipse-workspace\\biblioteca\\socios.csv", true);
			bw = new BufferedWriter(fw);
			for (Socio socio : listaSocios) {
				bw.write(socio.getSocio());
			}
			bw.close();
			System.out.println("Archivo modificado ..");
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					fw.close();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void mostrarListadoPrestamos() {
	
		BufferedReader br =null;
		
		try {
			br=new BufferedReader(new FileReader("C:\\Users\\picha\\eclipse-workspace\\biblioteca\\prestamo.csv"));
			
			do {
				
				this.listadoPrestamos.add(new Prestamo((br.readLine().split(";"))));
				
			}while(br.ready());
			
		} catch (Exception e) {
			System.out.println("Error: posible error en el fichero");
		}
		if (listadoPrestamos.isEmpty()) {
			System.out.println("\nNo hay registros.");
		} else {
			System.out.println("\n----------- Lista de Libros: ----------");
			for (Prestamo elemento : listadoPrestamos) { 
				System.out.println(elemento);
			}
		}
		
	
		}
	
	


	public static  void añadirPrestamoAFichero(ArrayList <Prestamo> listadoPrestamos) {
		FileWriter fw =null;
		BufferedWriter bw =null;
		try {
			 fw = new FileWriter("C:\\Users\\picha\\eclipse-workspace\\biblioteca\\prestamo.csv", false);
			 bw = new BufferedWriter(fw);
			for (Prestamo prestamo : listadoPrestamos) {
				bw.write(((Prestamo) prestamo).getPres());
			}
			bw.close();
			System.out.println("Archivo modificado ..");
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		} finally {
			if(fw != null ) {
				try {
					fw.close();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public ArrayList<Libro> getLibros(){
		return this.listadoLibros;
	}
	
	public ArrayList<Socio> getSocios(){
		return this.listaSocios;
	}
		
	public ArrayList<Prestamo> getPrestamos(){
		return listadoPrestamos;
	}
	public void mostrarListadoSocios() {
	BufferedReader br =null;
		
		try {
			br=new BufferedReader(new FileReader("C:\\Users\\picha\\eclipse-workspace\\biblioteca\\socios.csv"));
			
			do {
				
				this.listaSocios.add(new Socio((br.readLine().split(";"))));
				
			}while(br.ready());
			
		} catch (Exception e) {
			System.out.println("Error: posible error en el fichero");
		}
		if (listaSocios.isEmpty()) {
			System.out.println("\nNo hay registros.");
		} else {
			System.out.println("\n----------- Lista de Libros: ----------");
			for (Socio elemento : listaSocios) { 
				System.out.println(elemento);
			}
		}
	}

	public void mostrarListadoLibros() {
	BufferedReader br =null;
		
		try {
			br=new BufferedReader(new FileReader("C:\\Users\\picha\\eclipse-workspace\\biblioteca\\libros.csv"));
			
			do {
				
				this.listadoLibros.add(new Libro((br.readLine().split(";"))));
				
			}while(br.ready());
			
		} catch (Exception e) {
			System.out.println("Error: posible error en el fichero");
		}
		if (listadoLibros.isEmpty()) {
			System.out.println("\nNo hay registros.");
		} else {
			System.out.println("\n----------- Lista de Libros: ----------");
			for (Libro elemento : listadoLibros) { 
				System.out.println(elemento);
			}
		}
	}
	
	public void eliminarLibro(ArrayList<Libro> libro,Libro lib) {
		libro.remove(lib);
	}

	public void devolverPrestamo(ArrayList <Prestamo> listadoPrestamos) {
		Scanner sc = new Scanner(System.in);
		System.out.println("introduce tu dni:");
		
		String dni = sc.nextLine();
	
		Socio socio = comprobarDni(dni); 
	
		for(Prestamo elemento: listadoPrestamos) {
			if(socio.getLibroPrestado().equals(socio)) {
				listadoPrestamos.remove(elemento);
				System.out.println("Libro entregado con exito!");
			}else {
				System.out.println("no encontrado");
			}
		}
	
	}
	public void guardarFicheros() throws IOException{
	        
            
          
	}
	//Devuelve el nombre de la biblioteca
	public String getNombreBiblioteca() {
		return nombreBiblioteca;
	}
	
	@Override
	public String toString() {
		return "\n--------------- Registro de la Biblioteca: "+ getNombreBiblioteca() +" ---------------\n"
				+ "\nTotal Socios: " + listaSocios.size() 
				+ "\nTotal libros: " + listadoLibros.size() 
				+ "\nTotal reservas: "+ listadoPrestamos.size() + "\n";
	}
	
}
