package dbServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess implements Runnable {

	private Socket socket;

	public ClientProcess(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try {
			//为每一个socket建立读写通道
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("********** Welcome to HnuDB **********");
			writer.println();
			writer.flush();
//			new InteractProcess(reader, out).run();

		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			try {
				socket.close();
			} catch (IOException e) {

			}
		}
	}

}
