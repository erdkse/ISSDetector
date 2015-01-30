package issdetector.com.erdkse.apps.issdetector.objects;

public class ReturnObject {

	private String message;
	private ResponseObject[] response;
	private RequestObject request;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseObject[] getResponse() {
		return response;
	}

	public void setResponse(ResponseObject[] response) {
		this.response = response;
	}

	public RequestObject getRequest() {
		return request;
	}

	public void setRequest(RequestObject request) {
		this.request = request;
	}

}
