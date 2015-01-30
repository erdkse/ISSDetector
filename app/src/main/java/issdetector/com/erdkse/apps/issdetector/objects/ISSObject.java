package issdetector.com.erdkse.apps.issdetector.objects;

public class ISSObject {
	private ISSPositionObject iss_position;
	private String message;
	private long timestamp;

	public ISSPositionObject getIssPositionObject() {
		return iss_position;
	}

	public void setIssPositionObject(ISSPositionObject iss_position) {
		this.iss_position = iss_position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
