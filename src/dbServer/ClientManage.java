package cn.edu.hnu.dbserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ClientManage implements Runnable{
	private Thread t;
	private Vector vSocket;
	private Vector vMessage;
	
	public ClientManage() {
		System.out.println("Createing");
		vSocket = new Vector();
		vMessage = new Vector();
	}
	public void addSocket(Object o) {
		vSocket.addElement(o);
	}
	public void addMessage(String s) {
		vMessage.addElement(s);
	}
	public Vector getvSocket() {
		return vSocket;
	}
	public void setvSocket(Vector vSocket) {
		this.vSocket = vSocket;
	}
	public Vector getvMessage() {
		return vMessage;
	}
	public void setvMessage(Vector vMessage) {
		this.vMessage = vMessage;
	}
	public synchronized void processMessage() throws IOException {
		if(0 == vMessage.size()) 
			return;
		for(int i = 0; i < vMessage.size(); i ++) {
			for(int j = 0; j < vSocket.size(); j++) {
				Socket s = (Socket)vSocket.get(j);
				PrintWriter writer = new PrintWriter(s.getOutputStream());
				System.out.println(vMessage.get(i));
				writer.println(vMessage.get(i));
				writer.flush();
			}
		}
		for(int j = 0; j < vSocket.size(); j++) {
			Socket s = (Socket)vSocket.get(j);
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			writer.print("WhqDB> ");
			writer.flush();
		}
		vMessage.removeAllElements();
		
	}
	public void run() {
		try {
			while(true) {
				for(int i = vSocket.size() - 1; i >=0; i--) {
					Socket s = (Socket)vSocket.get(i);
					if(s.isClosed()) 
						vSocket.remove(i);
				}
				this.processMessage();
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void start() {
		System.out.println("Starting");
		if(t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
	
}
