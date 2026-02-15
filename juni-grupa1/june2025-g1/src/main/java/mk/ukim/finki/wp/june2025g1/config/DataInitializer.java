package mk.ukim.finki.wp.june2025g1.config;

import mk.ukim.finki.wp.june2025g1.model.Founder;
import mk.ukim.finki.wp.june2025g1.model.Industry;
import mk.ukim.finki.wp.june2025g1.service.FounderService;
import mk.ukim.finki.wp.june2025g1.service.StartupService;

import java.util.List;

public class DataInitializer {

    private final StartupService startupService;
    private final FounderService founderService;

    public DataInitializer(StartupService startupService, FounderService founderService) {
        this.startupService = startupService;
        this.founderService = founderService;
    }


    private Industry randomizeIndustry(int i) {
        if (i % 4 == 0) return Industry.AI;
        if (i % 4 == 1) return Industry.BIOTECH;
        if (i % 4 == 2) return Industry.FINTECH;
        return Industry.CYBERSECURITY;
    }

    public void initData() {
        for (int i = 1; i <= 3; i++) {
            this.founderService.create("Founder " + i, "founder" + i + "@example.com");
        }

        List<Founder> founders = this.founderService.listAll();

        for (int i = 1; i <= 10; i++) {
            String name = "Startup " + i;
            Double valuation = 500 + (i * 100.0);
            Integer yearFounded = 2010 + i;
            Industry period = this.randomizeIndustry(i);
            Long founderId = founders.get((i - 1) % founders.size()).getId();

            this.startupService.create(name, valuation, yearFounded, period, founderId);
        }
    }
}
