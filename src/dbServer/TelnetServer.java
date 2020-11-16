package dbServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;



public class TelnetServer {

	 private ServerSocket server = null;
	 //_start
	 
	 private final ExecutorService executor = 
			 Executors.newFixedThreadPool(100);
	 //_end
	
	 private int GIVEN_PORT=2223;//�˿ں�

	 public void telnetServer(String port) {
	    GIVEN_PORT = port != null ? 
	        		Integer.valueOf(port).intValue() : 0;
	    }
	 /*
	  * ����������
	  */
	 public void run() {

	        try {
	            // ��ָ���˿ںŽ�������
	            server = new ServerSocket(GIVEN_PORT );
	            System.out.println("Server running and listening on port : "
	                    + GIVEN_PORT );

	            while (true) {
	            	 //��ʼ����
	                Socket s = server.accept();
	                //�пͻ������ӽ���󣬼�����һ���̣߳�����socket��Ϊһ������������
	                executor.execute(new ClientProcess(s));
	            }

	        } catch (IOException e) {
	        	System.out.println(Level.WARNING+ e.toString()+" Shutting down the server..");
	        } finally {
	            executor.shutdown();
	        }

	    }

	    /**
	     * Checks if the server is running.
	     *
	     * @return
	     */
	    public boolean isRunning() {
	        return !server.isClosed();
	    }

	    /**
	     * �ر���������
	     *
	     * @throws IOException
	     */
	    public void shutDown() throws IOException {
	        if (server != null) {
	            server.close();
	        }

	    }
}
