package net.harshDeveloper.JournalApp.Dto;

import jakarta.annotation.security.DenyAll;
import lombok.*;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Component
public class UsersDto {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
