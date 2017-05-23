package Controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.ProyectosMaquina;

public class GReservas {
private Connection cn;
	
	public GReservas(Connection cn) {
		this.cn = cn;
	}

	public void consultarEventos(ArrayList<ProyectosMaquina> eventos) {
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM PROYECTOSMAQUINA ORDER BY COD_PR, COD_MA");
			
			while(rs.next()) {
				ProyectosMaquina pm = new ProyectosMaquina();
				pm.setCod_pr(rs.getString(1));
				pm.setCod_ma(rs.getString(2));
				pm.setFecha_inicio(rs.getString(3));
				pm.setFecha_fin(rs.getString(4));
				eventos.add(pm);
			}
			
			rs.close();
			st.close();
		}
		catch(Exception e) {
			System.out.println("Error al consultar los eventos - " + e);
		}
	}
}
