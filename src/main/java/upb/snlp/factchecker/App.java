package upb.snlp.factchecker;

import upb.snlp.factchecker.bean.Fact;
import upb.snlp.factchecker.bean.RDFTriple;
import upb.snlp.factchecker.core.TripletExtractor;
import upb.snlp.factchecker.dbpedia.Query;
import upb.snlp.factchecker.util.Constants;
import upb.snlp.factchecker.util.TSVReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class App {
    private static final String FACT_URI = "<http://swc2017.aksw.org/task2/dataset/";
    private static final String PROP_URI = "<http://swc2017.aksw.org/hasTruthValue>";
    private static final String TYPE = "<http://www.w3.org/2001/XMLSchema#double>";
    public static void main(String[] args) throws IOException {
        List<Fact> inputs = TSVReader.getFactsFromFile("src/main/resources/train.tsv");
        FileWriter fw = new FileWriter("src/main/resources/result.ttl");
        for(Fact i : inputs) {
            RDFTriple triplet;
            try {
                triplet = TripletExtractor.extractRDFTriplets(i.toString());
                if(triplet != null && triplet.getPredicate().equals(Constants.PREDICATE_BIRTH_PLACE)){
                    List<String> resultSet = Query.queryBirthPlaceData(triplet);
                    boolean isTrue = false;
                    for(String result: resultSet){
                        if(triplet.getObject().equals(result)){
                            isTrue = true;
                            break;
                        }
                        if(result.contains(triplet.getObject())){
                            isTrue = true;
                            break;
                        }
                        //TODO : Check jaccardian similarity between the words.
                    }
                    i.setTruthfulnessValue(isTrue ? 1.0f : -1.0f);
                }
                String line = FACT_URI + i.getFactId() + "> " + PROP_URI + " \"" + i.getTruthfulnessValue() + "\"^^" + TYPE + " .\n";
                fw.write(line);
            } catch (Exception e) {
                System.out.println("**************ERROR FOR" + i.toString() + "************");
            }
        }
        fw.close();

        System.out.println("End");
        /*String query = "New York City is Jay-Z's nascence place.";
        System.out.println("Query:\t" + query);
        RDFTriple triplet = TripletExtractor.extractRDFTriplets(query);
        System.out.println("\nTriplets:\t" + triplet.toString() + "\n");
        System.out.println("Results:\n" + Query.queryBirthPlaceData(triplet).toString() + "\n\n");*/
    }
}
