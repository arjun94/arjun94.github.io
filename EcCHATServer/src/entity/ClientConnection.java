package entity;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class ClientConnection {
	
	
	
	private DataInputStream in;
	private DataOutputStream out;

	public ClientConnection(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
	}

	public DataInputStream getIn() {
		return in;
	}

	public DataOutputStream getOut() {
		return out;
	}
	
}
