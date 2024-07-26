package mx.com.qtx.ejmSpSec.entidades;

public class Sugerencia {
	private String asunto;
	private String descripcion;
	
	private static int numQueja = 1;
	
	public Sugerencia() {
		super();
	}

	public Sugerencia(String asunto, String descripcion) {
		super();
		this.asunto = asunto;
		this.descripcion = descripcion;
		numQueja++;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static int getNumQueja() {
		return numQueja;
	}

	public static void setNumQueja(int numQueja) {
		Sugerencia.numQueja = numQueja;
	}

	@Override
	public String toString() {
		return "Sugerencia [asunto=" + asunto + ", descripcion=" + descripcion + "]";
	}

}
