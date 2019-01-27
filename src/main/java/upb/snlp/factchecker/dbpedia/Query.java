package upb.snlp.factchecker.dbpedia;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import upb.snlp.factchecker.bean.RDFTriple;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Query {

    private static JSONObject queryDBPedia(String queryString) throws UnsupportedEncodingException, UnirestException {

        HttpResponse<JsonNode> jsonResponse =
                Unirest.get(String.format(Constants.DBPEDIA_QUERY_BASE_URL, queryString))
                        .header("accept", "application/json")
                        .asJson();

        return jsonResponse.getBody().getObject()
                .getJSONObject(String.format(Constants.DBPEDIA_RESPONSE_ROOT_KEY, queryString));
    }

    public static List<String> queryBirthPlaceData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.BIRTH_PLACE_KEY);
    }

    public static List<String> querySpouseData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.SPOUSE_KEY);
    }

    public static List<String> queryAwardsData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.AWARD_KEY);
    }

    public static List<String> queryDeathPlaceData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.DEATH_PLACE_KEY);
    }

    public static List<String> queryLeaderData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.LEADER_KEY);
    }

    public static List<String> querySportsTeamData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.TEAM_KEY);
    }

    public static List<String> queryFoundationPlaceData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.FOUND_KEY);
    }

    public static List<String> queryActorsData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.STARS_KEY);
    }

    public static List<String> queryBookAuthorData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.AUTHOR_KEY);
    }

    public static List<String> querySubsidiaryData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.SUBSIDIARY_KEY);

        // some times a company has a parent company field in the json, search needs to be on object in that case, cardinality unknown
    }

    private static List<String> queryMultiValueDataFor(String queryString, String key) {
        List<String> values = new ArrayList<>();

        try {
            JSONObject subjectData = queryDBPedia(queryString);
            JSONArray entities = subjectData.getJSONArray(key);
            int arraySize = entities.length();

            for (int i = 0; i < arraySize; i++) {
                values.add(extractValue(entities.getJSONObject(i)));
            }

        } catch (UnsupportedEncodingException | UnirestException e) {
            e.printStackTrace();
        }

        return values;
    }

    // Jaccard similarity can be applied on this return value to match with likeliness
    private static String extractValue(JSONObject typeValuePair) throws UnsupportedEncodingException {
        String valueType = typeValuePair.getString("type");
        if (valueType.equalsIgnoreCase("uri")) {
            String value = typeValuePair.getString("value");
            int NEStartIndex = value.lastIndexOf("/") + 1;
            String namedEntity = URLDecoder.decode(value.substring(NEStartIndex).replace("_", " "), "UTF-8");
            return namedEntity.trim();
        } else {
            System.out.println("====================== UNKNOWN TYPE FOUND: " + valueType);
        }

        return null;
    }

    private static String encodeForURL(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8");
    }


}
