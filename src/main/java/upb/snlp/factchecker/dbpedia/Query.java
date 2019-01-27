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
                Unirest.get(String.format(DBPediaConstants.DBPEDIA_QUERY_BASE_URL, queryString))
                        .header("accept", "application/json")
                        .asJson();

        //TODO handle redirections

        return jsonResponse.getBody().getObject()
                .getJSONObject(String.format(DBPediaConstants.DBPEDIA_RESPONSE_ROOT_KEY, queryString));
    }

    public static Collection<String> queryAwardsData(String queryString) {
        Set<String> awards = queryMultiValueDataFor(queryString, DBPediaConstants.AWARD_KEY);
        awards.addAll(queryMultiValueDataFor(queryString, DBPediaConstants.ABSTRACT_KEY));

        return awards;
    }

    public static Collection<String> querySportsTeamData(String queryString) {
        //http://dbpedia.org/ontology/termPeriod may be interesting for historic teams
        return queryMultiValueDataFor(queryString, DBPediaConstants.TEAM_KEY);
    }

    public static Collection<String> querySubsidiaryData(String subjectQueryString) {

        Set<String> subsidiaries = queryMultiValueDataFor(subjectQueryString, DBPediaConstants.SUBSIDIARY_KEY);
//        subsidiaries.addAll(queryMultiValueDataFor(objectQueryString, DBPediaConstants.OWNING_COMPANY_KEY));

        // handle uri for List of companies as response

        return subsidiaries;

    }

    public static Set<String> queryMultiValueDataFor(String queryString, String key) {
        Set<String> values = new HashSet<>();

        try {
            JSONObject subjectData = getSubjectData(queryString);
            JSONArray entities = subjectData.getJSONArray(key);
            int arraySize = entities.length();

            for (int i = 0; i < arraySize; i++) {
                String extractedValue = extractValue(entities.getJSONObject(i));
                if(extractedValue != null)
                    values.add(extractedValue);
            }

        } catch (UnsupportedEncodingException | UnirestException | JSONException e) {
            //e.printStackTrace();
        }

        return values;
    }

    private static JSONObject getSubjectData(String queryString) throws UnsupportedEncodingException, UnirestException {
        return queryDBPedia(queryString);
    }

    // Jaccard similarity can be applied on this return value to match with likeliness
    private static String extractValue(JSONObject typeValuePair) throws UnsupportedEncodingException {
        String valueType = typeValuePair.getString("type");
        String lang = typeValuePair.has("lang") ? typeValuePair.getString("lang") : null;
        String value = null;

        if (valueType.equalsIgnoreCase("uri")) {
            value = typeValuePair.getString("value");
            int NEStartIndex = value.lastIndexOf("/") + 1;
            value = URLDecoder.decode(value.substring(NEStartIndex).replace("_", " "), "UTF-8").trim();
        }
        else if (valueType.equalsIgnoreCase("literal")) {
            if(lang == null) {
                value = typeValuePair.getString("value");
            }
            else
            {
                if(lang.equalsIgnoreCase("en"))
                {
                    value = typeValuePair.getString("value");
                }
            }
        }

        if(value == null && !typeValuePair.has("lang")){
            System.out.println("====================== UNKNOWN TYPE FOUND: " + valueType + "," + lang);
        }

        return value;
    }

    private static String encodeForURL(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8");
    }


}
