package biblioteca.clases.alexdr;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

public class Prestamo {
	private float total;
	private LocalDate fechaPrestamo, fechaEntrega;
	private boolean devuelto;
	private long dias;
	private Socio socio;
	private Libro libroPrestado;
	private String dni,titulo;
	private boolean estaReservado;
	
	public Prestamo(Socio socio, Libro libroPrestado) {
		this.socio = socio;
		this.libroPrestado = libroPrestado;
		
	}
	
	public Prestamo(String[]infoPrestamo) {
		this.dni=infoPrestamo[0];
		this.titulo=infoPrestamo[1];
		this.fechaPrestamo=LocalDate.parse(infoPrestamo[2]);
		if (infoPrestamo[2].equals("null")) {
			fechaEntrega = null;
		}
		else {
			fechaEntrega = LocalDate.parse(infoPrestamo[3]);
		}
		if (infoPrestamo[4].equals("false")) {
			estaReservado = false;
		}else {
			estaReservado = true;
		}
	}
	public Prestamo() {
		
		total = 0;
		fechaPrestamo = null;
		fechaEntrega = null;
		devuelto = false;
		dias = 0;
		
	}
	

	public Prestamo( float total, LocalDate fechaPrestamo, LocalDate fechaEntrega, boolean devuelto,long dias) {
	
		this.total = total;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaEntrega = fechaEntrega;
		this.devuelto = devuelto;
		this.dias = dias;
	
	}

	



	public float getTotal() {
		return total;
	}
	public long getDias() {
		
		return dias;
	}
	public void setDias() {
		dias = DAYS.between(fechaPrestamo, fechaEntrega);
		
	}
	public void setTotal(float precio) {
		
		this.total = precio * (getDias());
	}
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo() {
		this.fechaPrestamo = LocalDate.now();
	}
	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega() {
		this.fechaEntrega = LocalDate.now();	
	}
	public boolean isDevuelto() {
		return devuelto;
	}
	public void setDevuelto(boolean devuelto) {
		this.devuelto = devuelto;
	}
	 
	public String getPres() {
		return  socio.getDni() + ";"+  libroPrestado.getTitulo() + ";" + getTotal() + ";" + getFechaPrestamo() + ";" + getFechaEntrega() + ";" + isDevuelto()+";"+getDias()+";";
	}



	@Override
	public String toString() {
		
		String msj = "Reservas: \n"
				+ "Socio: " + socio.getDni() + " \n"
				+ "Libro Prestado: " + libroPrestado.getTitulo() + "\n"
				+ "Fecha Inicial: " + getFechaPrestamo() +"\n";
		if (isDevuelto()) {
			msj += "Fecha final: " + getFechaEntrega() + "\n"
					+ "Dias: " + getDias() + "\n"
				+ "Total: " + getTotal() + "\n";
		}
		
		return msj;
	}
	
}


