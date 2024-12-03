package proyectoFoto;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private final int PUERTO = 5000;
	private final int TAM_PAQUETE = 49;
	
	public void iniciar() {
		try {
			ServerSocket servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor arrancado.");
			Socket cliente = servidor.accept();
			OutputStream os =  cliente.getOutputStream();
			DataOutputStream salida = new DataOutputStream(os);;
			File fich = new File("Jument.jpg");
			int tam = (int) fich.length();
			salida.writeInt(tam);
			InputStream f = new FileInputStream("Jument.jpg");
			int veces = (int) (tam / TAM_PAQUETE);
			int resto = (int) (tam - veces * TAM_PAQUETE);
			byte leido[] = new byte[TAM_PAQUETE];
				for (int i = 0; i < veces; i++) {
					f.read(leido);
					salida.write(leido);
				}
				if (resto != 0) {
					f.read(leido, 0, resto); 
					salida.write(leido, 0, resto);
				}
				f.close();
				salida.close();
				servidor.close();
				cliente.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public static void main(String[] args) {
		 Servidor s = new Servidor();
		 s.iniciar();
		}
}


