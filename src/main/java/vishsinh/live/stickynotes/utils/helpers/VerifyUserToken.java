package vishsinh.live.stickynotes.utils.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vishsinh.live.stickynotes.repository.ActiveSessionsRepository;

import javax.security.auth.login.LoginException;
import java.util.UUID;


@Component
public class VerifyUserToken {

    @Autowired
    private ActiveSessionsRepository activeSessionsRepository;

    public void verifyToken(String token, String userIdHash) throws LoginException {
        if (!activeSessionsRepository.existsByUserIdHashAndToken(userIdHash, token.substring(7))) throw new LoginException("Invalid token");
    }
}
