package org.santanu.santanubrains.rxjava.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponse {

	private Integer errorCode;
	private String errorMessage;
	private String documentation;

	public ErrorResponse() {

	}

	public ErrorResponse(Integer errorCode, String errorMessage, String documentation) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.documentation = documentation;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

}
