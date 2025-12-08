package project2.dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AppUserAuthDto {
	private String email;
	private String password;
	private List<AuthDto>  authList;
}