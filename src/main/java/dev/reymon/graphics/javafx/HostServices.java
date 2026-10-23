package dev.reymon.graphics.javafx;

public class HostServices {

	private javafx.application.HostServices hostServices;
	
	public HostServices() {
	}
	
	public void setHostServices(javafx.application.HostServices hostServices) {
		this.hostServices = hostServices;
	}
	
	public javafx.application.HostServices getHostServices() {
		return this.hostServices;
	}
	
}
