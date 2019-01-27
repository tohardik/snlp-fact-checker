package upb.snlp.factchecker.dbpedia;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import upb.snlp.factchecker.bean.RDFTriple;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class Query {

    private static JSONObject queryDBPedia(String queryString) throws UnsupportedEncodingException, UnirestException {

        HttpResponse<JsonNode> jsonResponse =
                Unirest.get(String.format(Constants.DBPEDIA_QUERY_BASE_URL, encodeForURL(queryString)))
                        .header("accept", "application/json")
                        .asJson();

        // handle redirections


        return jsonResponse.getBody().getObject()
                .getJSONObject(String.format(Constants.DBPEDIA_RESPONSE_ROOT_KEY, encodeForURL(queryString)));
    }

    public static Collection<String> queryAwardsData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.AWARD_KEY);
    }

    public static Collection<String> querySportsTeamData(RDFTriple triple) {
        String queryString = triple.getSubject();
        return queryMultiValueDataFor(queryString, Constants.TEAM_KEY);
    }

    public static Collection<String> querySubsidiaryData(RDFTriple triple) {
        String subjectQueryString = triple.getSubject();
        String objectQueryString = triple.getObject();

        Set<String> subsidiaries = queryMultiValueDataFor(subjectQueryString, Constants.SUBSIDIARY_KEY);
        subsidiaries.addAll(queryMultiValueDataFor(objectQueryString, Constants.OWNING_COMPANY_KEY));

        // handle uri for List of companies as response

        return subsidiaries;

    }

    public static Set<String> queryMultiValueDataFor(String queryString, String key) {
        Set<String> values = new HashSet<>();

        try {
            JSONObject subjectData = queryDBPedia(queryString);
            JSONArray entities = subjectData.getJSONArray(key);
            int arraySize = entities.length();

            for (int i = 0; i < arraySize; i++) {
                values.add(extractValue(entities.getJSONObject(i)));
            }

        } catch (UnsupportedEncodingException | UnirestException | JSONException e) {
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
