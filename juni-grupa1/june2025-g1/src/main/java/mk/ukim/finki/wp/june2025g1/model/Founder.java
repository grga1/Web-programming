package mk.ukim.finki.wp.june2025g1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Founder {
    private Long id;
    private String name;
    private String email;

    public Founder(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
