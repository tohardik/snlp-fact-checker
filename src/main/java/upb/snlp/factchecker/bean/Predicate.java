package upb.snlp.factchecker.bean;

import upb.snlp.factchecker.dbpedia.DBPediaConstants;
import upb.snlp.factchecker.dbpedia.Query;
import upb.snlp.factchecker.util.Constants;

import java.util.Collection;

public enum Predicate {

    BIRTH_PLACE(Constants.PREDICATE_BIRTH_PLACE, DBPediaConstants.BIRTH_PLACE_KEY),
    SPOUSE(Constants.PREDICATE_SPOUSE, DBPediaConstants.SPOUSE_KEY),
    DEATH_PLACE(Constants.PREDICATE_DEATH_PLACE, DBPediaConstants.DEATH_PLACE_KEY),
    LEADER(Constants.PREDICATE_OFFICE, DBPediaConstants.LEADER_KEY), //Previously held offices are not present for some NE
    FOUND(Constants.PREDICATE_FOUNDATION_PLACE, DBPediaConstants.FOUND_KEY),
    STARS(Constants.PREDICATE_STARS, DBPediaConstants.STARS_KEY),
    AUTHOR(Constants.PREDICATE_AUTHOR, DBPediaConstants.AUTHOR_KEY),
    TEAM(Constants.PREDICATE_TEAM, DBPediaConstants.TEAM_KEY) {
        @Override
        public Collection<String> queryDBPediaFor(RDFTriple triple) {
            return Query.querySportsTeamData(triple.getSubject());
        }
    },
    AWARD(Constants.PREDICATE_AWARD, DBPediaConstants.AWARD_KEY) {
        @Override
        public Collection<String> queryDBPediaFor(RDFTriple triple) {
            return Query.queryAwardsData(triple.getSubject());
        }
    },
    SUBSIDIARY(Constants.PREDICATE_SUBSIDIARY, DBPediaConstants.SUBSIDIARY_KEY) {
        @Override
        public Collection<String> queryDBPediaFor(RDFTriple triple) {
            return Query.querySubsidiaryData(triple.getSubject());

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
