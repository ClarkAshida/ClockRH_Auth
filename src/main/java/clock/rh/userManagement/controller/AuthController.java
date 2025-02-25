package clock.rh.userManagement.controller;

import clock.rh.userManagement.config.security.TokenService;
import clock.rh.userManagement.dto.auth.LoginDataDTO;
import clock.rh.userManagement.dto.auth.LoginResponseDTO;
import clock.rh.userManagement.dto.users.DetailUserDataDTO;
import clock.rh.userManagement.model.User;
import jakarta.validation.Valid;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    @Transactional
    public ResponseEntity<?> login(@RequestBody @Valid LoginDataDTO loginData) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginData.email(), loginData.password());
            var authentication = authManager.authenticate(authenticationToken);

            var user = (User) authentication.getPrincipal();
            var tokenJWT = tokenService.generateToken(user);

            var userData = new DetailUserDataDTO(user);
            var response = new LoginResponseDTO(tokenJWT, userData);

            Message message = new Message(("Login realizado com sucesso: " + user.getEmail()).getBytes());
            rabbitTemplate.send("login.realizado", message);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no login: " + exception.getMessage());
        }
    }
}
