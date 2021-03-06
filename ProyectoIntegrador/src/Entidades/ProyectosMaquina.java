package Entidades;

public class ProyectosMaquina {
	private String nombre;
	private String cod_pr;
	private String cod_ma;
	private String fecha_inicio;
	private String fecha_fin;
	
	public ProyectosMaquina(String nombre, String cod_pr, String cod_ma, String fecha_inicio, String fecha_fin) {
		this.nombre = nombre;
		this.cod_pr = cod_pr;
		this.cod_ma = cod_ma;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
	}
	
	public ProyectosMaquina() {
		this.nombre = "";
		this.cod_pr = "";
		this.cod_ma = "";
		this.fecha_inicio = "";
		this.fecha_fin = "";
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCod_pr() {
		return cod_pr;
	}

	public void setCod_pr(String cod_pr) {
		this.cod_pr = cod_pr;
	}

	public String getCod_ma() {
		return cod_ma;
	}

	public void setCod_ma(String cod_ma) {
		this.cod_ma = cod_ma;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}
	
	public String getFecha_inicioFormatted() {
		return fecha_inicio.substring(0, fecha_inicio.indexOf(' '));
	}

	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFecha_fin() {
		return fecha_fin;
	}
	
	public String getFecha_finFormatted() {
		return fecha_fin.substring(0, fecha_fin.indexOf(' '));
	}

	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
}
