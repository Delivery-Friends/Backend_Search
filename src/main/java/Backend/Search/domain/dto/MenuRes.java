package Backend.Search.domain.dto;

import Backend.Search.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRes {
    String name;

    public MenuRes(Menu menu) {
        this.name = menu.getName();
    }
}
