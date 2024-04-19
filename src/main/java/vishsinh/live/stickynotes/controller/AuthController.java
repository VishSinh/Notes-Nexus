package vishsinh.live.stickynotes.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vishsinh.live.stickynotes.dto.UserIdDTO;
import vishsinh.live.stickynotes.dto.auth.LoginDTO;
import vishsinh.live.stickynotes.dto.auth.SignUpDTO;
import vishsinh.live.stickynotes.service.AuthService;
import vishsinh.live.stickynotes.utils.helpers.ResponseObj;
import vishsinh.live.stickynotes.utils.helpers.VerifyUserToken;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VerifyUserToken tokenVerifier;

    @PostMapping("/signup")
    public ResponseObj signUp(@Valid @RequestBody SignUpDTO requestBody, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = authService.signUpUser(requestBody.getEmail(), requestBody.getUsername(), requestBody.getPassword());

            return new ResponseObj(true, "User Created", responseObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseObj.fromException(e);
        }

    }

    @PostMapping("/login")
    public ResponseObj login(@Valid @RequestBody LoginDTO requestBody, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = authService.loginUser(requestBody.username, requestBody.password);

            return new ResponseObj(true, "User Logged In", responseObj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseObj authLogout(@Valid @RequestBody UserIdDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            authService.logoutUser(requestBody.userIdHash);

            return new ResponseObj(true, "User Logged Out", HttpStatus.OK);
        }  catch (Exception e) {
            return ResponseObj.fromException(e);
        }
    }
}
