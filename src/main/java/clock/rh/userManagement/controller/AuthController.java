package clock.rh.userManagement.controller;

import clock.rh.userManagement.config.security.TokenJWTDataDTO;
import clock.rh.userManagement.config.security.TokenService;
import clock.rh.userManagement.dto.auth.LoginDataDTO;
import clock.rh.userManagement.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
            return ResponseEntity.ok(new TokenJWTDataDTO(tokenJWT));

        } catch (Exception exception) {
            String errorMessage = "Login error: " + exception.getClass().getSimpleName() + " - " + exception.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }
}
