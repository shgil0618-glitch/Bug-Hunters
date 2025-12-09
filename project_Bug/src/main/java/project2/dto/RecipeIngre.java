package project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngre {
    private int ingreMapId;
    private String ingreName;
    private String ingreNum;
}
