package mx.com.qtx.ejmSpSec.seguridad.rest;

public interface IResultadoOperacion {
	Object getObjResultadoOk();
	void setObjResultadoOk(Object objResultadoOk);
	boolean todoOk();
	void agregarError(int codError, String adicion);
	String getResumenErrores();
}
