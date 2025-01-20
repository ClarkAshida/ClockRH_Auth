package clock.rh.userManagement.dto.auth;

import clock.rh.userManagement.dto.users.DetailUserDataDTO;

public record LoginResponseDTO(String token, DetailUserDataDTO userData) {}
