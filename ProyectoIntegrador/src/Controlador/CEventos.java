package Controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
		
		cargarEventos();
	}
	
	private void cargarEventos() {
		for (Eventos e : eventos) {
			DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
			tabla.addRow(new Object[] {e.getCod_ev(), e.getFecha(), e.getMentor(), e.getCategoria(), e.getDuracion(), e.getLugar()});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == vEventos.btnInsertar) {
			if (vEventos.btnInsertar.getText().equals("Insertar")) {
				vEventos.tfCodigo.setText(String.valueOf(Integer.parseInt(eventos.get(eventos.size()-1).getCod_ev())+1));
				vEventos.btnInsertar.setText("Guardar");
			}
			else if (vEventos.btnInsertar.getText().equals("Guardar")) {
				aņadirEvento();
				vEventos.btnInsertar.setText("Insertar");
			}
		}
		else if (obj == vEventos.btnBorrar) {
			eliminarEvento();
		}
	}

	private void eliminarEvento() {
		if (vEventos.tfCodigo.getText().equals("")) {
			vEventos.lblLblerror.setForeground(Color.RED);
			vEventos.lblLblerror.setText("Haga clic en el evento que desea borrar por favor");
		}
		
		else {
			// Eliminar el evento de la base de datos
			int cod = Integer.parseInt(vEventos.tfCodigo.getText());
			gEventos.eliminarEvento(cod);
			
			// Eliminar el evento de la tabla
			DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
			int fila = vEventos.table.getSelectedRow();
			System.out.println(fila);
			tabla.removeRow(fila);
			
			vEventos.lblLblerror.setForeground(Color.GREEN.darker());
			vEventos.lblLblerror.setText("Evento eliminado correctamente");
			
			limpiar();
		}
	}

	private void aņadirEvento() {
		if (vEventos.table.getRowCount() >= 0) {
			vEventos.btnBorrar.setEnabled(true);
			vEventos.btnModificar.setEnabled(true);
		}
		if (this.vEventos.tfCodigo.getText().equals("") || this.vEventos.tfFecha.getText().equals("") || this.vEventos.tfMentor.getText().equals("") || this.vEventos.tfCategoria.getText().equals("") || this.vEventos.tfDuracion.getText().equals("") || this.vEventos.tfLugar.getText().equals("")) {
			vEventos.lblLblerror.setForeground(Color.RED);
			vEventos.lblLblerror.setText("Rellene todos los campos por favor");
			//JOptionPane.showMessageDialog(null, "Rellene todos los campos por favor");
			limpiar();
		}
		else {
			Eventos e = new Eventos(vEventos.tfCodigo.getText(), vEventos.tfFecha.getText(), vEventos.tfMentor.getText(), vEventos.tfCategoria.getText(), vEventos.tfDuracion.getText(), vEventos.tfLugar.getText());
			eventos.add(e);
			
			// Aņadir el evento a la tabla
			DefaultTableModel tabla = (DefaultTableModel) vEventos.table.getModel();
			tabla.addRow(new Object[] {e.getCod_ev(), e.getFecha(), e.getMentor(), e.getCategoria(), e.getDuracion(), e.getLugar()});
			
			// Aņadir el evento a la base de datos
			gEventos.aņadirEvento(e);
			limpiar();
		}
	}
	
	public void limpiar() {
		vEventos.tfCodigo.setText("");
		vEventos.tfFecha.setText("");
		vEventos.tfMentor.setText("");
		vEventos.tfCategoria.setText("");
		vEventos.tfDuracion.setText("");
		vEventos.tfLugar.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int fila = vEventos.table.rowAtPoint(e.getPoint());
		
		vEventos.tfCodigo.setText(eventos.get(fila).getCod_ev());
		vEventos.tfFecha.setText(eventos.get(fila).getFecha());
		vEventos.tfMentor.setText(eventos.get(fila).getMentor());
		vEventos.tfCategoria.setText(eventos.get(fila).getCategoria());
		vEventos.tfDuracion.setText(eventos.get(fila).getDuracion());
		vEventos.tfLugar.setText(eventos.get(fila).getLugar());
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
