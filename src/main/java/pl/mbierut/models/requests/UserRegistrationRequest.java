package pl.mbierut.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationRequest {
    private String userName;
    private String email;
    private String password;
}
