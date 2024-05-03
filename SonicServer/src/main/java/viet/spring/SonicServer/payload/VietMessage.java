package viet.spring.SonicServer.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class VietMessage {
    private int statusCode;
    private String message;
    private String data;
	public VietMessage(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
    
}
