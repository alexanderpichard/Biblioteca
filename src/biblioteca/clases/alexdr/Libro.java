package biblioteca.clases.alexdr;

public class Libro {
	//Atributos de la clase Libro
	
	private int ISBN;
	private String titulo;
	private String autor;
	private int ejemplares;
	private float precio;
	private boolean disponible=true;
	

	public Libro(String listLibro []){
		this.ISBN=Integer.parseInt(listLibro[0]);
		this.titulo=listLibro[1];
		this.autor=listLibro[2];
		this.ejemplares=Integer.parseInt(listLibro[3]);
		this.precio=Float.parseFloat(listLibro[4]);
	}
	//Constructor defecto
	public Libro() {
		this.ISBN=0;
		this.autor="";
		this.titulo="";
		this.ejemplares=0;
		this.precio=0.0f;
		this.disponible=true;
	}
	
	//Constructor con parametros
	public Libro(int isbn, String titulo, String autor,int ejemplares,float precio) {
		this.ISBN = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
		this.disponible = true;
	}

	//Metodos set y get de la clase libro
	
	public int getIsbn() {
		return ISBN;
	}
	
	public void setIsbn(int isbn) {
		this.ISBN = isbn;
	}

	public int getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(int ejemplares) {
		this.ejemplares = ejemplares;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	public String getLibro() {
		return getIsbn() + ";"+ getTitulo() + ";" + getAutor() + ";" + getEjemplares() + ";" + getPrecio() + "\n";
	}
	//tostring de libro
	@Override
	public String toString() {
		return  "\nTitulo: " + getTitulo()
				+ "\nISBN: " + getIsbn() 
				+ "\nAutor: " + getAutor()
				+"\nEjemplares: " + getEjemplares()
				+ "\nEstado: " + libroDisponible()
				+"\nPrecio: " +getPrecio() + " €\n";
	}
	
	public String libroDisponible() {
		String mensaje ="";
		if(isDisponible()&& this.ejemplares >=1) {
			mensaje="Disponible";
		}else {
			
			mensaje="Reservado";
		}
		return mensaje;
		
	}
	//Mostrara todos los ejemplares con el mismo nombre jusntos
	public int compareTo(Libro libro) {
		return titulo.compareTo(libro.titulo);
	}
	

}


