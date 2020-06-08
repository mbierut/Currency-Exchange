package pl.mbierut.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationRequest {
    public String userName;
    public String email;
    public String password;
}
