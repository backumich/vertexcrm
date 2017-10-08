package ua.com.vertex.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetDto {
    private int id;
    private String email;
    private String uuid;
    private LocalDateTime creationTime;
    @Size(min = 5, max = 30)
    private String rawPassword;
    @Size(min = 5, max = 30)
    private String repeatPassword;
}
