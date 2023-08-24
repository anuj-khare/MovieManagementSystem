package com.Personal.MovieManagementSystem.Service.resource;

import com.Personal.MovieManagementSystem.Model.MyUser;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class userRequest {
    @NotBlank(message = "UserName cannot be left empty")
    private String userName;
    @NotBlank(message = "Password cannot be left empty")
    private String password;
    private Boolean isAdmin;
    public MyUser getUserFromRequest(){
        return MyUser.builder()
                .userName(this.userName)
                .password(this.password)
                .authorities(this.isAdmin == true?"admin":"user")
                .build();
    }
}
