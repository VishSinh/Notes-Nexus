package vishsinh.live.stickynotes.utils.helpers;

import jakarta.validation.ValidationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;

import javax.security.auth.login.LoginException;

public class ResponseObj {
    public Boolean success;
    public String message;
    public Object data;
    public int code;

    public ResponseObj(Boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.data = null;
        this.code = status.value();

        printResponse();
    }

    public ResponseObj(Boolean success, String message, Object data, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = status.value();

        printResponse();
    }

    public static ResponseObj fromException(Exception e) {
        e.printStackTrace();
        System.out.println("Error: " + e);
        if (e instanceof BadRequestException) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (e instanceof ValidationException) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (e instanceof LoginException){
            return  new ResponseObj(false, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Internal Server Error: " + e.getMessage());
            return new ResponseObj(false, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void printResponse() {
        System.out.println("Response: { "+ "\n\tsuccess: " + this.success+ ", \n\tmessage: " + this.message+ ", \n\tdata: " + this.data+ ", \n\tcode: " + this.code+ " \n}");
    }
}
