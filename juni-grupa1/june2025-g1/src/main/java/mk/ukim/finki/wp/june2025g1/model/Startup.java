package mk.ukim.finki.wp.june2025g1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Startup {

    private Long id;
    private String name;
    private Double valuation;
    private Integer yearFounded;
    private boolean active = true;
    private Industry industry;
    private Founder founder;

    public Startup(String name, Double valuation, Integer yearFounded, Industry industry, Founder founder) {
        this.name = name;
        this.valuation = valuation;
        this.yearFounded = yearFounded;
        this.industry = industry;
        this.founder = founder;
    }

    public Startup(String name, Double valuation, Integer yearFounded, boolean active, Industry industry, Founder founder) {
        this.name = name;
        this.valuation = valuation;
        this.yearFounded = yearFounded;
        this.active = active;
        this.industry = industry;
        this.founder = founder;
    }
}
