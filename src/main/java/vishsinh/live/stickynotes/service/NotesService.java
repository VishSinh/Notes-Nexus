package vishsinh.live.stickynotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishsinh.live.stickynotes.entity.ActivityLogs;
import vishsinh.live.stickynotes.entity.Notes;
import vishsinh.live.stickynotes.entity.User;
import vishsinh.live.stickynotes.entity.UserDetails;
import vishsinh.live.stickynotes.repository.ActivityLogsRepository;
import vishsinh.live.stickynotes.repository.NotesRepository;
import vishsinh.live.stickynotes.repository.UserDetailsRepository;
import vishsinh.live.stickynotes.repository.UserRepository;

import java.util.*;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ActivityLogsRepository activityLogsRepository;


    public Object createNote(String userIdHash, String title, String description, boolean publicVisibility) {
        System.out.println("User ID Hash: " + userIdHash + " Title: " + title + " Description: " + description + " Public Visibility: " + publicVisibility);

        // Create a new note
        Notes note = new Notes(userIdHash, title, description, publicVisibility);
        notesRepository.save(note);

        System.out.println("Note created: " + note);

        Object updateDateTime = note.getLastUpdateDateTime() != null ? note.getLastUpdateDateTime() : "N/A";

        UserDetails userDetails = userDetailsRepository.findByUserIdHash(userIdHash);

        ActivityLogs activityLog = new ActivityLogs(
                userIdHash,
                "Created a new note",
                new HashMap<>(Map.of("note_id" , note.getNoteId())).toString());
        activityLogsRepository.save(activityLog);


        return new HashMap<>(Map.of(
                "note_id", note.getNoteId(),
                "title", note.getTitle(),
                "user_id_hash", note.getUserIdHash(),
                "name", userDetails.getName(),
                "description", note.getDescription(),
                "public_visibility", note.isPublicVisibility(),
                "create_date_time", note.getCreateDateTime(),
                "update_date_time", updateDateTime));
    }


    public Object fetchOwnNotes(String userIdHash, int page, int rowsPerPage) {
        // Query the database to get notes of user with userIdHash that are not deleted
        List<Notes> notes = notesRepository.findByUserIdHashAndDeletedOrderByCreateDateTimeDesc(userIdHash, false);

        System.out.println("rowsPerPage: " + rowsPerPage + " page: " + page);
        System.out.println("Notes before pagination " + notes.size());

        notes = notes.subList(page * rowsPerPage, Math.min(notes.size(), (page + 1) * rowsPerPage));
        System.out.println("Notes after pagination " + notes.size());


        for (Notes note : notes) {
            System.out.println(note);
        }

        // Create a HashMap without the deleted attribute
        List<Object> notesMap = new ArrayList<>();

        UserDetails userDetails = userDetailsRepository.findByUserIdHash(userIdHash);

        for(Notes note : notes) {
            HashMap<String, Object> noteMap = new HashMap<>();
            noteMap.put("note_id", note.getNoteId());
            noteMap.put("title", note.getTitle());
            noteMap.put("user_id_hash", note.getUserIdHash());
            noteMap.put("name", userDetails.getName());
            noteMap.put("description", note.getDescription());
            noteMap.put("public_visibility", note.isPublicVisibility());
            noteMap.put("create_date_time", note.getCreateDateTime());
            noteMap.put("update_date_time", note.getLastUpdateDateTime());
            notesMap.add(noteMap);
        }

        return new HashMap<>(Map.of("notes", notesMap));

    }

    public Object fetchNotes(String userIdHash, int page, int rowsPerPage, int type) {

        User user = userRepository.findByUserIdHash(userIdHash);

        List<Notes> notes;
        List<UserDetails> allUsers = userDetailsRepository.findAll();

        if (user.isAdmin()) {
            // Case 1: If user is admin and type is 0, fetch all notes
            // Case 2: If user is admin and type is 1, fetch only public notes
            // Case 3: If user is admin and type is 2, fetch only private notes
            notes = type == 0 ? notesRepository.findAllByOrderByCreateDateTimeDesc() :
                    notesRepository.findByPublicVisibilityOrderByCreateDateTimeDesc(type == 1);
        } else {
            // If user is not admin, fetch only public notes
            notes = notesRepository.findByPublicVisibilityOrderByCreateDateTimeDesc(true);
        }

        // Pagination
        notes = notes.subList(page * rowsPerPage, Math.min(notes.size(), (page + 1) * rowsPerPage));


        // Create a HashMap without the deleted attribute
        List<Object> notesMap = new ArrayList<>();

        for(Notes note : notes) {
            if(note.isDeleted()) continue;

            // Find the user details associated with the note
            UserDetails userDetails = null;
            for(UserDetails userDetail : allUsers) {
                if(userDetail.getUserIdHash().equals(note.getUserIdHash())) {
                    userDetails = userDetail;
                    break;
                }
            }

            if (userDetails == null) {
                System.out.println("User details not found for user with ID: " + note.getUserIdHash());
                continue;
            }

            HashMap<String, Object> noteMap = new HashMap<>();
            noteMap.put("note_id", note.getNoteId());
            noteMap.put("user_id_hash", note.getUserIdHash());
            noteMap.put("name", userDetails.getName());
            noteMap.put("title", note.getTitle());
            noteMap.put("description", note.getDescription());
            noteMap.put("public_visibility", note.isPublicVisibility());
            noteMap.put("create_date_time", note.getCreateDateTime());
            noteMap.put("update_date_time", note.getLastUpdateDateTime());
            notesMap.add(noteMap);
        }

        return new HashMap<>(Map.of("notes", notesMap));
    }

    public Object deleteNote(String userIdHash, String noteId) {
        Notes note = notesRepository.findByNoteId(UUID.fromString(noteId));
        if (note == null) throw new RuntimeException("Note not found");

        boolean isAdmin = userRepository.findByUserIdHash(userIdHash).isAdmin();
        if(!note.getUserIdHash().equals(userIdHash) && !isAdmin) throw new RuntimeException("User not authorized to delete this note");

        note.setDeleted(true);
        note.setDeletedBy(userIdHash);
        note.setDeleteDateTime(new java.util.Date());
        notesRepository.save(note);

        ActivityLogs activityLog = new ActivityLogs(
                userIdHash,
                "Deleted a new note",
                new HashMap<>(Map.of("note_id" , note.getNoteId())).toString());
        activityLogsRepository.save(activityLog);

        return new HashMap<>(Map.of(
                "note_id", note.getNoteId(),
                "deleted", note.isDeleted(),
                "deleted_by", note.getDeletedBy(),
                "delete_date_time", note.getDeleteDateTime()));
    }

    public Object editNote(String userIdHash, String noteId, String title, String description, boolean publicVisibility) {
        Notes note = notesRepository.findByNoteId(UUID.fromString(noteId));
        if (note == null) throw new RuntimeException("Note not found");

        boolean isAdmin = userRepository.findByUserIdHash(userIdHash).isAdmin();
        if(!note.getUserIdHash().equals(userIdHash) && !isAdmin) throw new RuntimeException("User not authorized to edit this note");

        note.setTitle(title);
        note.setDescription(description);
        note.setPublicVisibility(publicVisibility);
        note.setUpdated(true);
        note.setUpdatedBy(userIdHash);
        note.setLastUpdateDateTime(new java.util.Date());
        notesRepository.save(note);

        ActivityLogs activityLog = new ActivityLogs(
                userIdHash,
                "Edited a new note",
                new HashMap<>(Map.of("note_id" , note.getNoteId())).toString());
        activityLogsRepository.save(activityLog);

        return new HashMap<>(Map.of(
                "note_id", note.getNoteId(),
                "title", note.getTitle(),
                "description", note.getDescription(),
                "public_visibility", note.isPublicVisibility(),
                "create_date_time", note.getCreateDateTime()));
    }
}
