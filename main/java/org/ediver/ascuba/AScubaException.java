package org.ediver.ascuba;

public class AScubaException extends Exception {
	private static final long serialVersionUID = -1870685529541848828L;
	private String alert;
	public AScubaException(String a){
		alert = a;
	}
	public String getAlert(){
		return alert;
	}
}
