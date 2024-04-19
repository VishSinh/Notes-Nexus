package vishsinh.live.stickynotes.controller;

import org.springframework.web.bind.annotation.*;
import vishsinh.live.stickynotes.dto.notes.*;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import vishsinh.live.stickynotes.service.NotesService;
import vishsinh.live.stickynotes.utils.helpers.ResponseObj;
import vishsinh.live.stickynotes.utils.helpers.VerifyUserToken;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @Autowired
    private VerifyUserToken tokenVerifier;

    @PostMapping("/create")
    public ResponseObj createNote(@Valid @RequestBody CreateNoteDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = notesService.createNote(requestBody.userIdHash, requestBody.title, requestBody.description, requestBody.publicVisibility);
            System.out.println(responseObj);

            return new ResponseObj(true, "Note Created", responseObj, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/fetch")
    public ResponseObj fetchNotes(@Valid @RequestBody FetchNotesDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = notesService.fetchNotes(requestBody.userIdHash, requestBody.page, requestBody.rowsPerPage, requestBody.type);

            return new ResponseObj(true, "Notes Fetched", responseObj, HttpStatus.OK);
        }catch (Exception e){
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/fetch/own")
    public ResponseObj fetchOwnNotes(@Valid @RequestBody FetchNotesDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = notesService.fetchOwnNotes(requestBody.userIdHash, requestBody.page, requestBody.rowsPerPage);

            return new ResponseObj(true, "Notes Fetched", responseObj, HttpStatus.OK);
        }catch (Exception e){
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/delete")
    public ResponseObj deleteNote(@Valid @RequestBody DeleteNoteDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = notesService.deleteNote(requestBody.userIdHash, requestBody.noteId);

            return new ResponseObj(true, "Note Deleted Successfully", responseObj, HttpStatus.OK);
        }catch (Exception e){
            return ResponseObj.fromException(e);
        }
    }

    @PostMapping("/edit")
    public ResponseObj editNote(@Valid @RequestBody EditNoteDTO requestBody, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        try{
            tokenVerifier.verifyToken(token, requestBody.userIdHash);

            if (bindingResult.hasErrors())  throw new ValidationException(bindingResult.getFieldErrors().toString());

            Object responseObj = notesService.editNote(requestBody.userIdHash, requestBody.noteId, requestBody.title, requestBody.description, requestBody.publicVisibility);

            return new ResponseObj(true, "Note Edited Successfully", responseObj, HttpStatus.OK);
        }catch (Exception e){
            return ResponseObj.fromException(e);
        }
    }
}
