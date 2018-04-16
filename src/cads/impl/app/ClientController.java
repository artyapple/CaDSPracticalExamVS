package cads.impl.app;

public class ClientController {
	
	ClientView view;
	
	public ClientController(ClientView view) {
		this.view = view;
	}


	public void open() {
		System.out.println("AUF");
	}

	public void close() {
		System.out.println("ZU");
	}

}
