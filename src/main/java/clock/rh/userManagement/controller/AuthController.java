package clock.rh.userManagement.controller;

import clock.rh.userManagement.dto.auth.LoginDataDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity login(@RequestBody @Valid LoginDataDTO loginData){
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(loginData.email(), loginData.password());
            var authentication = authManager.authenticate(authenticationToken);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Erro no login: " + exception.getMessage());
        }
    }
}
