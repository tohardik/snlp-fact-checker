package upb.snlp.factchecker.core;

import upb.snlp.factchecker.bean.RDFTriple;
import upb.snlp.factchecker.util.Constants;

/**
 * Author: Nandeesh Patel
 */
public class TripletExtractor {

    /**
     *
     * @param fact this is the fact from which we need to extract the subject, object and predicate.
     * @return
     *
     * This function extracts the subject, object and predicate based on various categories and returns them as instance of RDFTriplet class.
     */
    public static RDFTriple extractRDFTriplets(String fact){
        String originalFact = fact;
        fact = fact.toLowerCase();
        if (fact.contains("award is")) {
            return createTriplets(originalFact, "award is", Constants.PREDICATE_AWARD, false);
        } else if (fact.contains("honor is")) {
            return createTriplets(originalFact, "honor is", Constants.PREDICATE_AWARD, false);
        } else if (fact.contains("award.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_AWARD, true);
        } else if (fact.contains("honour.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_AWARD, true);
        } else if (fact.contains("birth place is")) {
            return createTriplets(originalFact, "birth place is", Constants.PREDICATE_BIRTH_PLACE, false);
        } else if (fact.contains("nascence place is")) {
            return createTriplets(originalFact, "nascence place is", Constants.PREDICATE_BIRTH_PLACE, false);
        } else if (fact.contains("birth place.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_BIRTH_PLACE, true);
        } else if (fact.contains("nascence place.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_BIRTH_PLACE, true);
        } else if (fact.contains("death place is")) {
            return createTriplets(originalFact, "death place is", Constants.PREDICATE_DEATH_PLACE, false);
        } else if (fact.contains("last place is")) {
            return createTriplets(originalFact, "last place is", Constants.PREDICATE_DEATH_PLACE, false);
        } else if (fact.contains("death place.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_DEATH_PLACE, true);
        } else if (fact.contains("last place.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_DEATH_PLACE, true);
        } else if (fact.contains("spouse is")) {
            return createTriplets(originalFact, "spouse is", Constants.PREDICATE_SPOUSE, false);
        } else if (fact.contains("better half is")) {
            return createTriplets(originalFact, "better half is", Constants.PREDICATE_SPOUSE, false);
        } else if (fact.contains("spouse.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_SPOUSE, true);
        } else if (fact.contains("better half.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_SPOUSE, true);
        } else if (fact.contains("office is")) {
            return createTriplets(originalFact, "office is", Constants.PREDICATE_OFFICE, false);
        } else if (fact.contains("role is")) {
            return createTriplets(originalFact, "role is", Constants.PREDICATE_OFFICE, false);
        } else if (fact.contains("office.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_OFFICE, true);
        } else if (fact.contains("role.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_OFFICE, true);
        } else if(fact.contains("team is")) {
            return createTriplets(originalFact, "team is", Constants.PREDICATE_TEAM, false);
        } else if(fact.contains("squad is")) {
            return createTriplets(originalFact, "squad is", Constants.PREDICATE_TEAM, false);
        } else if(fact.contains("team.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_TEAM, true);
        } else if(fact.contains("squad.")) {
            return createTriplets(originalFact, "is", Constants.PREDICATE_TEAM, true);
        } else if(fact.contains("foundation place is")){
            return createTriplets(originalFact, "foundation place is", Constants.PREDICATE_FOUNDATION_PLACE, false);
        } else if(fact.contains("innovation place is")){
            return createTriplets(originalFact, "innovation place is", Constants.PREDICATE_FOUNDATION_PLACE, false);
        } else if(fact.contains("foundation place.")){
            return createTriplets(originalFact, "is", Constants.PREDICATE_FOUNDATION_PLACE, true);
        } else if(fact.contains("innovation place.")){
            return createTriplets(originalFact, "is", Constants.PREDICATE_FOUNDATION_PLACE, true);
        } else if(fact.contains("author is")){
            return createTriplets(originalFact, "author is", Constants.PREDICATE_AUTHOR, false);
        } else if(fact.contains("generator is")){
            return createTriplets(originalFact, "generator is", Constants.PREDICATE_AUTHOR, false);
        } else if(fact.contains("author.")){
            return createTriplets(originalFact, "is", Constants.PREDICATE_AUTHOR, true);
        } else if(fact.contains("generator.")){
            return createTriplets(originalFact, "is", Constants.PREDICATE_AUTHOR, true);
        } else if(fact.contains("subsidiary is")){
            return createTriplets(originalFact, "subsidiary is", Constants.PREDICATE_SUBSIDIARY, false);
        } else if(fact.contains("subordinate is")){
            return createTriplets(originalFact, "subordinate is", Constants.PREDICATE_SUBSIDIARY, false);
        } else if(fact.contains("subsidiary.")){
            return createTriplets(originalFact, "is", Constants.PREDICATE_SUBSIDIARY, true);
        } else if(fact.contains("subordinate.")){
            return createTriplets(originalFact, "is", Constants.PREDICATE_SUBSIDIARY, true);
        } else if(fact.contains(" stars")){
            return createTriplets(originalFact, "stars", Constants.PREDICATE_STARS, false);
        } else if(fact.startsWith("stars")){
            String subject = originalFact.substring("Stars ".length(), originalFact.indexOf(" has been"));
            String object = originalFact.substring(originalFact.lastIndexOf(' ') + 1);
            return new RDFTriple(clean(subject), Constants.PREDICATE_STARS, clean(object));
        }
        return null;
    }

    /**
     *
     * @param fact this is the actual fact from which we need to split the triplets.
     * @param key this is the word based on which the sentence is split.
     * @param predicate this is the predicate of the fact.
     * @return
     *
     * This function takes the fact, key word and the predicate as its parameters.
     * Subject is the set of words occurring before the key word and object is the set of words occurring after key word if inverted is false.
     * Subject and object are swapped if inverted flag is true.
     */
    private static RDFTriple createTriplets(String fact, String key, String predicate, boolean inverted){
        if(inverted){
            key = " " + key + " ";
        }
        int keyIndex = fact.indexOf(key);
        String subject = fact.substring(0, keyIndex);
        String object = fact.substring(keyIndex + key.length());
        if (!inverted)
            return new RDFTriple(clean(subject), clean(object), predicate);
        else
            return new RDFTriple(clean(object), clean(subject), predicate);

    }

    /**
     *
     * @param partOfSpeech A set of words which will be normalized to get correct part of speech.
     * @return
     *
     * This function takes a set of words and normalizes it by removing apostrophe and dots from them.
     */
    private static String clean(String partOfSpeech) {
        partOfSpeech = partOfSpeech.contains("'") ? partOfSpeech.substring(0, partOfSpeech.lastIndexOf("'")) : partOfSpeech;
        return partOfSpeech.replaceAll("[.]$","").trim();
    }

}
