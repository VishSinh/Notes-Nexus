package vishsinh.live.stickynotes.controller;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vishsinh.live.stickynotes.dto.UserIdDTO;
import vishsinh.live.stickynotes.dto.notes.FetchNotesDTO;
import vishsinh.live.stickynotes.dto.user.FetchActivityLogsDTO;
import vishsinh.live.stickynotes.dto.user.GetUserDetailsDTO;
import vishsinh.live.stickynotes.dto.user.PromoteToAdminDTO;
import vishsinh.live.stickynotes.dto.user.SaveUserDetailsDTO;
import vishsinh.live.stickynotes.service.UserService;
import vishsinh.live.stickynotes.utils.helpers.ResponseObj;
import vishsinh.live.stickynotes.utils.helpers.VerifyUserToken;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerifyUserToken tokenVerifier;

    @PostMapping("/details/save")
    public ResponseObj saveUserDetails(@Valid @RequestBody SaveUserDetailsDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = userService.saveUserDetails(requestBody.userIdHash, requestBody.name, requestBody.age, requestBody.bio);

            return new ResponseObj(true, "User Details Saved", responseObj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/details/get")
    public ResponseObj getUserDetails(@Valid @RequestBody GetUserDetailsDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = userService.getUserDetails(requestBody.userIdHash, requestBody.toFindUserIdHash);

            return new ResponseObj(true, "User Details Retrieved", responseObj, HttpStatus.OK);

        }  catch (Exception e) {
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/promote")
    public ResponseObj promoteToAdmin(@Valid @RequestBody PromoteToAdminDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = userService.promoteToAdmin(requestBody.userIdHash, requestBody.toPromoteUserIdHash);

            return new ResponseObj(true, "User Promoted to Admin", responseObj, HttpStatus.OK);

        }  catch (Exception e) {
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/logs")
    public ResponseObj fetchLogs(@Valid @RequestBody FetchActivityLogsDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = userService.fetchActivityLogs(requestBody.userIdHash, requestBody.page, requestBody.rowsPerPage);

            return new ResponseObj(true, "Fetched Logs", responseObj, HttpStatus.OK);

        }  catch (Exception e) {
            return ResponseObj.fromException(e);
        }
    }
}
