package Controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import Entidades.Eventos;
import OracleAccess.OracleAccess;
import Vista.VEventos;

public class CEventos implements ActionListener, MouseListener {
	VEventos vEventos;
	GEventos gEventos;
	OracleAccess bbdd;
	ArrayList<Eventos> eventos = new ArrayList<Eventos>();
	
	public CEventos(VEventos vEventos, OracleAccess bbdd) {
		this.vEventos = vEventos;
		this.bbdd = bbdd;
		this.gEventos = new GEventos(bbdd.getCn());
		this.gEventos.consultarEventos(eventos);
		
		this.vEventos.tfCodigo.setEnabled(false);
		this.vEventos.btnModificar.setEnabled(false);
		this.vEventos.btnBorrar.setEnabled(false);
		this.vEventos.dateChooser.getJCalendar().setTodayButtonVisible(true);
		this.vEventos.dateChooser.getJCalendar().setTodayButtonText("Hoy");
		
		cargarEventos();
	}
	
	private void cargarEventos() {
		for (Eventos e : eventos) {
			DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
			tabla.addRow(new Object[] {e.getCod_ev(), e.getFechaFormatted(), e.getMentor(), e.getCategoria(), e.getDuracion(), e.getLugar()});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == vEventos.btnInsertar) {
			if (vEventos.btnInsertar.getText().equals("Insertar")) {
				limpiar();
				vEventos.tfCodigo.setText(String.valueOf(Integer.parseInt(eventos.get(eventos.size()-1).getCod_ev())+1));
				vEventos.btnInsertar.setText("Guardar");
				vEventos.btnModificar.setEnabled(false);
				vEventos.btnBorrar.setEnabled(false);
				// Con este try-catch introducimos por defecto la fecha de hoy
				try {
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date today = new Date();
					System.out.println(dateFormat.format(today));
					vEventos.dateChooser.setDate(today);
				}
				catch (Exception ex) {
					System.out.println("Error obteniendo fecha - " + ex);
				}
			}
			else if (vEventos.btnInsertar.getText().equals("Guardar")) {
				a�adirEvento();
				vEventos.btnInsertar.setText("Insertar");
				vEventos.btnModificar.setEnabled(true);
				vEventos.btnBorrar.setEnabled(true);
			}
		}
		else if (obj == vEventos.btnBorrar) {
			eliminarEvento();
		}
		else if (obj == vEventos.btnModificar) {
			modificarEvento();
		}
	}
	
	private void modificarEvento() {
		if (this.vEventos.tfCodigo.getText().equals("")) {
			vEventos.lblError.setForeground(Color.RED);
			vEventos.lblError.setText("Haga clic en el evento que desea modificar por favor");
		}
		else {
			String fecha = vEventos.dateChooser.getJCalendar().getYearChooser().getYear() + "-" +
			              (vEventos.dateChooser.getJCalendar().getMonthChooser().getMonth()+1) + "-" +
			               vEventos.dateChooser.getJCalendar().getDayChooser().getDay();
			
			Eventos e = new Eventos(vEventos.tfCodigo.getText(), fecha, vEventos.tfMentor.getText(), vEventos.tfCategoria.getText(), vEventos.tfDuracion.getText(), vEventos.tfLugar.getText());
			gEventos.modificarEvento(vEventos, e);
			
			DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
			int fila = vEventos.table.getSelectedRow();
			tabla.setValueAt(e.getCod_ev(), fila, 0);
			tabla.setValueAt(fecha, fila, 1);
			tabla.setValueAt(e.getMentor(), fila, 2);
			tabla.setValueAt(e.getCategoria(), fila, 3);
			tabla.setValueAt(e.getDuracion(), fila, 4);
			tabla.setValueAt(e.getLugar(), fila, 5);
			
			limpiar();
			this.vEventos.btnModificar.setEnabled(false);
			this.vEventos.btnBorrar.setEnabled(false);
		}
	}

	private void eliminarEvento() {
		if (vEventos.tfCodigo.getText().equals("")) {
			vEventos.lblError.setForeground(Color.RED);
			vEventos.lblError.setText("Haga clic en el evento que desea borrar por favor");
		}
		
		else {
			int cod = Integer.parseInt(vEventos.tfCodigo.getText());
			// Eliminar el evento de la base de datos
			if (gEventos.eliminarEvento(cod)) {
				// Eliminar el evento de la tabla
				DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
				int fila = vEventos.table.getSelectedRow();
				tabla.removeRow(fila);
				eventos.remove(fila);
				
				vEventos.lblError.setForeground(Color.GREEN.darker());
				vEventos.lblError.setText("Evento eliminado correctamente");
			}
			
			else {
				vEventos.lblError.setForeground(Color.RED);
				vEventos.lblError.setText("El evento no se pudo eliminar");
			}
			
			limpiar();
			this.vEventos.btnModificar.setEnabled(false);
			this.vEventos.btnBorrar.setEnabled(false);
		}
	}

	private void a�adirEvento() {
		String fecha = vEventos.dateChooser.getJCalendar().getYearChooser().getYear() + "-" +
                      (vEventos.dateChooser.getJCalendar().getMonthChooser().getMonth()+1) + "-" +
                       vEventos.dateChooser.getJCalendar().getDayChooser().getDay();
		
		if (vEventos.table.getRowCount() >= 0) {
			vEventos.btnBorrar.setEnabled(true);
			vEventos.btnModificar.setEnabled(true);
		}
		if (this.vEventos.tfCodigo.getText().equals("") || fecha.equals("") || this.vEventos.tfMentor.getText().equals("") || this.vEventos.tfCategoria.getText().equals("") || this.vEventos.tfDuracion.getText().equals("") || this.vEventos.tfLugar.getText().equals("")) {
			vEventos.lblError.setForeground(Color.RED);
			vEventos.lblError.setText("Rellene todos los campos por favor");
			//System.out.println("Fecha:" + fecha);
			limpiar();
		}
		else {
			Eventos e = new Eventos(vEventos.tfCodigo.getText(), fecha, vEventos.tfMentor.getText(), vEventos.tfCategoria.getText(), vEventos.tfDuracion.getText(), vEventos.tfLugar.getText());
			eventos.add(e);
			
			// A�adir el evento a la tabla
			DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
			tabla.addRow(new Object[] {e.getCod_ev(), e.getFecha(), e.getMentor(), e.getCategoria(), e.getDuracion(), e.getLugar()});
			
			// A�adir el evento a la base de datos
			gEventos.a�adirEvento(e);
			limpiar();
			vEventos.lblError.setForeground(Color.GREEN.darker());
			vEventos.lblError.setText("Evento a�adido correctamente");
		}
	}
	
	public void limpiar() {
		vEventos.tfCodigo.setText("");
		vEventos.tfMentor.setText("");
		vEventos.tfCategoria.setText("");
		vEventos.tfDuracion.setText("");
		vEventos.tfLugar.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		
		if (obj == vEventos.table) {
			this.vEventos.btnModificar.setEnabled(true);
			this.vEventos.btnBorrar.setEnabled(true);
			
			int fila = vEventos.table.rowAtPoint(e.getPoint());
			
			vEventos.tfCodigo.setText(eventos.get(fila).getCod_ev());
			vEventos.tfMentor.setText(eventos.get(fila).getMentor());
			vEventos.tfCategoria.setText(eventos.get(fila).getCategoria());
			vEventos.tfDuracion.setText(eventos.get(fila).getDuracion());
			vEventos.tfLugar.setText(eventos.get(fila).getLugar());
			
			// Este try-catch hace la misma funcion que las lineas de arriba
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(eventos.get(fila).getFecha());
				vEventos.dateChooser.setDate(date);
			}
			catch (ParseException ex) {
				System.out.println("Error obteniendo fecha - " + ex);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
