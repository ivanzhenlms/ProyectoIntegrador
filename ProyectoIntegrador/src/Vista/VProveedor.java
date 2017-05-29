package Vista;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controlador.CProveedor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class VProveedor extends JFrame {

	public JPanel contentPane;
	public JTable table;
	public JTextField tfCif;
	public JTextField tfNombre;
	public JTextField tfCorreo;
	public JTextField tfTelefono;
	public JTextField tfDireccion;
	public JTextField tfCodigoPostal;
	public JTextArea tfDescripcion;
	public JLabel lblError;

	public VProveedor() {
		setTitle("Proveedores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 510, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(pantalla.width/3, pantalla.height/5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 320, 474, 166);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"CIF", "Nombre", "Correo", "Tel�fono", "Direcci�n", "C�digo Postal", "Descripci�n"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblCif = new JLabel("CIF");
		lblCif.setBounds(10, 11, 84, 14);
		contentPane.add(lblCif);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 46, 84, 14);
		contentPane.add(lblNombre);
		
		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(10, 79, 84, 14);
		contentPane.add(lblCorreo);
		
		JLabel lblTelfono = new JLabel("Tel�fono");
		lblTelfono.setBounds(10, 114, 84, 14);
		contentPane.add(lblTelfono);
		
		JLabel lblDireccin = new JLabel("Direcci�n");
		lblDireccin.setBounds(10, 148, 84, 14);
		contentPane.add(lblDireccin);
		
		JLabel lblCdigoPostal = new JLabel("C�digo Postal");
		lblCdigoPostal.setBounds(10, 181, 100, 14);
		contentPane.add(lblCdigoPostal);
		
		JLabel lblDescripcin = new JLabel("Descripci�n");
		lblDescripcin.setBounds(10, 215, 84, 14);
		contentPane.add(lblDescripcin);
		
		tfCif = new JTextField();
		tfCif.setEnabled(false);
		tfCif.setBounds(104, 8, 160, 20);
		contentPane.add(tfCif);
		tfCif.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(104, 43, 160, 20);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfCorreo = new JTextField();
		tfCorreo.setBounds(104, 76, 160, 20);
		contentPane.add(tfCorreo);
		tfCorreo.setColumns(10);
		
		tfTelefono = new JTextField();
		tfTelefono.setBounds(104, 111, 160, 20);
		contentPane.add(tfTelefono);
		tfTelefono.setColumns(10);
		
		tfDireccion = new JTextField();
		tfDireccion.setBounds(104, 145, 160, 20);
		contentPane.add(tfDireccion);
		tfDireccion.setColumns(10);
		
		tfCodigoPostal = new JTextField();
		tfCodigoPostal.setBounds(104, 178, 160, 20);
		contentPane.add(tfCodigoPostal);
		tfCodigoPostal.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(104, 215, 160, 70);
		contentPane.add(scrollPane_1);
		
		tfDescripcion = new JTextArea();
		scrollPane_1.setViewportView(tfDescripcion);
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(10, 295, 474, 14);
		contentPane.add(lblError);
	}
	
	public void setControlador(CProveedor c) {
		table.addMouseListener(c);
	}
}
