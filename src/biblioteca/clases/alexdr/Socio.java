package biblioteca.clases.alexdr;

public class Socio {
	//Atributos de la clase
	private String dni;
	private String nombre;
	private String apellidos;
	private int telefono;
	private String email;
	private Libro libroPrestado;
	
	
	//he sobrecargado el metodo como hemos visto en clase
	public Socio(String listSocio []){
		this.dni=listSocio[0];
		this.nombre=listSocio[1];
		this.apellidos=listSocio[2];
		this.telefono=Integer.parseInt(listSocio[3]);
		this.email=listSocio[4];
	}
	//Constructor defecto
	public Socio() {
		this.dni="";
		this.nombre="";
		this.apellidos="";
		this.telefono=0;
		this.email="";
	}
	//Constructor con parametros
	public Socio(String dni, String nombre, String apellidos, int telefono,String email) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		
	}
	
	//Metodos sett y gett de la clase Socio
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Libro getLibroPrestado() {
		return libroPrestado;
	}

	

	public void setLibroPrestado(Libro libroPrestado) {
		this.libroPrestado = libroPrestado;
	}
	
	public String getSocio() {
		return getDni() + ";" + getNombre() + ";" + getApellidos() + ";" + getTelefono() + ";" + getEmail() + "\n";
	}
	//toString de socio
	@Override
	public String toString() {
		return "\n--------. Socio: .---------"
				+ "\nDni: " + getDni() 
				+ "\nNombre: " + getNombre() 
				+ "\nApellidos: " + getApellidos()
				+ "\nTelefono: " + getTelefono()
				+ "\nEmail: " + getEmail();
	}
	//Sobrescribo metodo equals para establecer criterio al introducir socios y que no haya duplicados 
	@Override
	public boolean equals(Object obj) {
		
		return dni.equals(((Socio)obj).dni);
	}
	//Establezco el criterio de ordenacion de arraylist de lectores por orden alfabetico de nombres
	/*public int compareTo(Socio o) {
		
		return nombre.compareTo(o.nombre);
	}*/
	
	
}

