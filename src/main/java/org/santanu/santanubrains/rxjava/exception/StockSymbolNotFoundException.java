package org.santanu.santanubrains.rxjava.exception;

public class StockSymbolNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8069433236174721953L;
	
	public StockSymbolNotFoundException(String message) {
		
		super(message);
	}

}
