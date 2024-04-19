package vishsinh.live.stickynotes.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import vishsinh.live.stickynotes.entity.ActivityLogs;
import vishsinh.live.stickynotes.entity.Notes;
import vishsinh.live.stickynotes.entity.User;
import vishsinh.live.stickynotes.entity.UserDetails;
import vishsinh.live.stickynotes.repository.ActivityLogsRepository;
import vishsinh.live.stickynotes.repository.NotesRepository;
import vishsinh.live.stickynotes.repository.UserDetailsRepository;
import vishsinh.live.stickynotes.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ActivityLogsRepository activityLogsRepository;

    public Object saveUserDetails( String userIdHash, String name, int age, String bio) {

        // Create a new user details record
        UserDetails userDetails = new UserDetails(userIdHash, name, age, bio);
        userDetailsRepository.save(userDetails);

        return new HashMap<>(Map.of(
                "name", userDetails.getName(),
                "age", userDetails.getAge(),
                "bio", userDetails.getBio(),
                "profile_picture", userDetails.getProfilePicture()));
    }

    public Object getUserDetails(String userIdHash, String toFindUserIdHash) throws BadRequestException {

        System.out.println("User ID Hash: " + userIdHash + " To Find User ID Hash: " + toFindUserIdHash);

        // check if type of toFindUserIdHash is string
        if(toFindUserIdHash == null){
            UserDetails userDetails = userDetailsRepository.findByUserIdHash(userIdHash);

            if(userDetails == null) throw new BadRequestException("User details not found");

            User user = userRepository.findByUserIdHash(userIdHash);

            List<Notes> notes = notesRepository.findByUserIdHashAndDeletedOrderByCreateDateTimeDesc(userIdHash, false);

            return new HashMap<>(Map.of(
                    "name", userDetails.getName(),
                    "age", userDetails.getAge(),
                    "bio", userDetails.getBio(),
                    "profile_picture", userDetails.getProfilePicture(),
                    "no_of_notes", notes.size(),
                    "is_user_admin", user.isAdmin()));
        }

        UserDetails userDetails = userDetailsRepository.findByUserIdHash(toFindUserIdHash);

        if(userDetails == null) throw new BadRequestException("User details not found");

        User user = userRepository.findByUserIdHash(toFindUserIdHash);

        return new HashMap<>(Map.of(
                "name", userDetails.getName(),
                "age", userDetails.getAge(),
                "bio", userDetails.getBio(),
                "user_id_hash", userDetails.getUserIdHash(),
                "profile_picture", userDetails.getProfilePicture(),
                "is_user_admin", user.isAdmin()));
    }

    public Object promoteToAdmin(String userIdHash, String toPromoteUserIdHash) throws BadRequestException {

        User user = userRepository.findByUserIdHash(userIdHash);

        if(!user.isAdmin()) throw new BadRequestException("User not authorized to perform this action");

        User toPromoteUser = userRepository.findByUserIdHash(toPromoteUserIdHash);

        if(toPromoteUser == null) throw new BadRequestException("User to promote not found");
        else if(toPromoteUser.isAdmin()) throw new BadRequestException("User is already an admin");

        toPromoteUser.setAdmin(true);
        userRepository.save(toPromoteUser);

        ActivityLogs activityLogs = new ActivityLogs(
                userIdHash,
                "Promoted a User to Admin",
                new HashMap<>(Map.of( "promoted_user_id_hash" , toPromoteUserIdHash)).toString());
        activityLogsRepository.save(activityLogs);

        return new HashMap<>(Map.of("user_id_hash", toPromoteUser.getUserIdHash(), "is_admin", toPromoteUser.isAdmin()));
    }


    public Object fetchActivityLogs(String userIdHash, int page, int rowsPerPage) throws BadRequestException{

        boolean isAdmin = userRepository.findByUserIdHash(userIdHash).isAdmin();

        if(!isAdmin) throw new BadRequestException("User is not admin");

        List<ActivityLogs> activityLogs = activityLogsRepository.findAll();
        activityLogs = activityLogs.subList(page * rowsPerPage, Math.min(activityLogs.size(), (page + 1) * rowsPerPage));

        List<UserDetails> allUserDetails = userDetailsRepository.findAll();

        List<Object> response = new ArrayList<>();

        for (ActivityLogs log: activityLogs){
            UserDetails userDetails = null;
            for(UserDetails userDetail : allUserDetails) {
                if(userDetail.getUserIdHash().equals(log.getActivityByUserIdHash())) {
                    userDetails = userDetail;
                    break;
                }
            }

            if(userDetails == null) continue;

            HashMap<String, Object> logMap = new HashMap<>();
            logMap.put("activity_by_name", userDetails.getName());
            logMap.put("activity", log.getActivity());
            logMap.put("details", log.getDetails());
            logMap.put("create_date_time", log.getCreateDateTime());
            response.add(logMap);
        }

        return new HashMap<>(Map.of("activities", response));
    }
}
