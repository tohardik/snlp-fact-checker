package upb.snlp.factchecker.bean;

import upb.snlp.factchecker.dbpedia.Query;

import java.util.Collection;

public enum Predicate {

    BIRTH_PLACE("birth place", "http://dbpedia.org/ontology/birthPlace"),
    SPOUSE("spouse", "http://dbpedia.org/ontology/spouse"),
    DEATH_PLACE("death place", "http://dbpedia.org/ontology/deathPlace"),
    LEADER("office", "http://dbpedia.org/ontology/office"),
    FOUND("found", "http://dbpedia.org/ontology/foundationPlace"),
    STARS("stars", "http://dbpedia.org/ontology/starring"),
    AUTHOR("author", "http://dbpedia.org/ontology/author"),
    TEAM("team", "http://dbpedia.org/ontology/team") {
        @Override
        public Collection<String> queryDBPediaFor(RDFTriple triple) {
            return Query.querySportsTeamData(triple);
        }
    },
    AWARD("award", "http://dbpedia.org/ontology/award") {
        @Override
        public Collection<String> queryDBPediaFor(RDFTriple triple) {
            return Query.queryAwardsData(triple);
        }
    },
    SUBSIDIARY("subsidiary", "http://dbpedia.org/ontology/subsidiary") {
        @Override
        public Collection<String> queryDBPediaFor(RDFTriple triple) {
            return Query.querySubsidiaryData(triple);

        }
    };

    private final String identifier;
    private final String key;

    Predicate(String identifier, String key) {
        this.identifier = identifier;
        this.key = key;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getKey() {
        return key;
    }

    public static Predicate getForIdentifier(String identifier)
    {
        for(Predicate p : Predicate.values())
        {
            if(p.identifier.equals(identifier)) {
                return p;
            }
        }

        return null;
    }

    public Collection<String> queryDBPediaFor(RDFTriple triple) {
        return Query.queryMultiValueDataFor(triple.getSubject(), this.getKey());
    }
}
