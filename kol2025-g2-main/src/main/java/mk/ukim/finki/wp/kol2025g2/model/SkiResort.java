package mk.ukim.finki.wp.kol2025g2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkiResort {

    private Long id;
    private String name;
    private String location;

    public SkiResort(String name, String location) {
        this.name = name;
        this.location = location;
    }


}
