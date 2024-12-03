package proyectoFoto;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Cliente extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final int PUERTO = 5000;
	private final int TAM_PAQUETE = 49;
	private JPanel contentPane;
	private JLabel foto;
	String urlFoto = "copia.jpg";
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Cliente() {
		getContentPane().setLayout(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			Socket cliente = new Socket("localhost", PUERTO);
			System.out.println("Conexión realizada con servidor");
			DataInputStream entrada = new DataInputStream(cliente.getInputStream());
			int tam = entrada.readInt();
			int veces = (int) (tam / TAM_PAQUETE);
			int resto = (int) (tam - veces * TAM_PAQUETE);
			byte recibido[] = new byte[TAM_PAQUETE];
			FileOutputStream f = new FileOutputStream("copia.jpg");
			for (int i = 0; i < veces; i++) {
				entrada.read(recibido); 
				f.write(recibido); 
			}
			if (resto != 0) {
				entrada.read(recibido, 0, resto); 
				f.write(recibido, 0, resto);
			}
			f.close();
			entrada.close();
			cliente.close();
		} catch (FileNotFoundException e) {
			urlFoto = "imagen-no-disponible.jpg";
		} catch (IOException e) {
			urlFoto = "imagen-no-disponible.jpg";
		}
	
		
		ImageIcon fotoE = new ImageIcon(urlFoto);
		
		foto = new JLabel(fotoE);
		foto.setBounds(0, 0, 660, 542);
		getContentPane().add(foto);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 581);	
	}
}
