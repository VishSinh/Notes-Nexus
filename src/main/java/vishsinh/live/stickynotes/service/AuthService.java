package vishsinh.live.stickynotes.service;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishsinh.live.stickynotes.entity.ActiveSessions;
import vishsinh.live.stickynotes.entity.User;
import vishsinh.live.stickynotes.repository.ActiveSessionsRepository;
import vishsinh.live.stickynotes.repository.UserDetailsRepository;
import vishsinh.live.stickynotes.repository.UserRepository;
import vishsinh.live.stickynotes.utils.helpers.JwtTokenProvider;
import vishsinh.live.stickynotes.utils.helpers.Sha256HashGenerator;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ActiveSessionsRepository activeSessionsRepository;

    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    @Autowired
    private Sha256HashGenerator Sha256HashGenerator;



    public Object signUpUser(String email, String username, String password) throws BadRequestException {

        if (userRepository.existsByEmail(email)) throw new BadRequestException("Email already in use. Please login instead.");

        if (userRepository.existsByUsername(username)) throw new BadRequestException("Username already in use. Please login instead.");

        String passwordHash = Sha256HashGenerator.generateSha256Hash(password);

        int noOfUsers = userRepository.findAll().size();
        boolean isAdmin = noOfUsers == 0;

        User user = new User(passwordHash, email, username, isAdmin);

        System.out.println("User: " + user.getUserId() + " " + user.getEmail() + " " + user.getUsername() + " " + user.getPassword());

        // Set the user id hash to the hashed value of the user id
        user.setUserIdHash(Sha256HashGenerator.generateSha256Hash(user.getUserId().toString()));
        userRepository.save(user);

        // Generate a token to create a new active session
        String token = JwtTokenProvider.generateToken(user.getUserId().toString());

        ActiveSessions activeSession = new ActiveSessions(user.getUserIdHash(), token);
        activeSessionsRepository.save(activeSession);

        return new HashMap<>(Map.of(
                "user_id_hash", activeSession.getUserIdHash(),
                "token", activeSession.getToken(),
                "is_admin", user.isAdmin(),
                "details_exist", false));
    }


    @Transactional
    public Object loginUser(String username, String password) throws BadRequestException {

        User user = userRepository.findByUsername(username);

        if (user == null)  throw new BadRequestException("Error: User not found");

        // Check if the password is correct
        if (!user.getPassword().equals(Sha256HashGenerator.generateSha256Hash(password))) throw new BadRequestException("Error: Invalid password");

        // Delete any existing active sessions for the user
        String userIdHash = user.getUserIdHash();
        if(activeSessionsRepository.existsByUserIdHash(userIdHash)) activeSessionsRepository.deleteByUserIdHash(userIdHash);

        // Generate a new token and create a new active session
        String token = JwtTokenProvider.generateToken(user.getUserId().toString());
        ActiveSessions activeSession = new ActiveSessions(user.getUserIdHash(), token);
        activeSessionsRepository.save(activeSession);

        boolean detailsExist = userDetailsRepository.existsByUserIdHash(user.getUserIdHash());

        return new HashMap<>(Map.of(
                "user_id_hash", activeSession.getUserIdHash(),
                "token", activeSession.getToken(),
                "is_admin", user.isAdmin(),
                "details_exist", detailsExist));
    }

    @Transactional
    public void logoutUser(String userIdHash) throws BadRequestException {
        if(!activeSessionsRepository.existsByUserIdHash(userIdHash)) throw new BadRequestException("User not logged in");
        activeSessionsRepository.deleteByUserIdHash(userIdHash);
    }
}
