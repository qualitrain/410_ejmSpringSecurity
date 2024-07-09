package mx.com.qtx.ejmSpSec.entidades;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import mx.com.qtx.ejmSpSec.util.Util;

@Entity
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="per_id")
	private long idPersona;
	@Column(name="per_ap_paterno")
	private String apPaterno;
	@Column(name="per_ap_materno")
	private String apMaterno;
	@Column(name="per_nombres")
	private String nombres;
	@Column(name="per_fec_nac")
	private Date fecNacimiento;
	
	public Persona() {
		super();
	}

	public Persona(long idPersona, String apPaterno, String apMaterno, String nombres) {
		super();
		this.idPersona = idPersona;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombres = nombres;
	}

	public Persona(long idPersona, String apPaterno, String apMaterno, String nombres, Date fecNacimiento) {
		super();
		this.idPersona = idPersona;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombres = nombres;
		this.fecNacimiento = fecNacimiento;
	}

	public long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFecNacimiento() {
		return fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}
	
	public String getNombreCompleto() {
		return this.apPaterno + " " + this.apMaterno + " " + this.nombres;
	}
	
	public String getFechaNacAAAAMMDD() {
		return Util.getFechaNacAAAAMMDD(this.fecNacimiento);
	}
	
	public String toStringFancy() {
		return String.format("%3d %-40s: %s",
				this.idPersona,this.getNombreCompleto(), this.getFechaNacAAAAMMDD() );
	}

	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", apPaterno=" + apPaterno + ", apMaterno=" + apMaterno
				+ ", nombres=" + nombres + ", fecNacimiento=" + fecNacimiento + "]";
	}

}
