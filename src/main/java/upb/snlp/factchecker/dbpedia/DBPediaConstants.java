package upb.snlp.factchecker.dbpedia;

public class DBPediaConstants {

    public static final String DBPEDIA_QUERY_BASE_URL = "http://dbpedia.org/data/%s.json";
    public static final String DBPEDIA_RESPONSE_ROOT_KEY = "http://dbpedia.org/resource/%s";

    // keys
    public static final String BIRTH_PLACE_KEY = "http://dbpedia.org/ontology/birthPlace";  //A
    public static final String SPOUSE_KEY = "http://dbpedia.org/ontology/spouse"; //A
    public static final String AWARD_KEY = "http://dbpedia.org/ontology/award"; //A , send abstract section
    public static final String DEATH_PLACE_KEY = "http://dbpedia.org/ontology/deathPlace"; //A
    public static final String LEADER_KEY = "http://dbpedia.org/ontology/office"; //A, not available for previously held offices
    public static final String TEAM_KEY = "http://dbpedia.org/ontology/team"; //A //http://dbpedia.org/ontology/termPeriod may be interesting for historic teams
    public static final String FOUND_KEY = "http://dbpedia.org/ontology/foundationPlace"; //A
    public static final String STARS_KEY = "http://dbpedia.org/ontology/starring"; //A
    public static final String AUTHOR_KEY = "http://dbpedia.org/ontology/author"; //A
    public static final String SUBSIDIARY_KEY = "http://dbpedia.org/ontology/subsidiary"; //A
    public static final String OWNING_COMPANY_KEY = "http://dbpedia.org/ontology/owningCompany"; //A , Subsidiary alternative



}
