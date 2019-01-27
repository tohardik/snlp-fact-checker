package upb.snlp.factchecker;

import upb.snlp.factchecker.bean.Fact;
import upb.snlp.factchecker.bean.Predicate;
import upb.snlp.factchecker.bean.RDFTriple;
import upb.snlp.factchecker.core.TripletExtractor;
import upb.snlp.factchecker.util.TSVReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    private static final String FACT_URI = "<http://swc2017.aksw.org/task2/dataset/";
    private static final String PROP_URI = "<http://swc2017.aksw.org/hasTruthValue>";
    private static final String TYPE = "<http://www.w3.org/2001/XMLSchema#double>";
    public static void main(String[] args) throws IOException {
        List<Fact> inputs = TSVReader.getFactsFromFile("src/main/resources/test.tsv");
        /*List<Fact> inputs = new ArrayList<>();
        Fact fact = new Fact();
        fact.setTruthfulnessValue(0.0f);
        fact.setTruthfulness(false);
        fact.setFactId(3610545);
        fact.setStatement("Stars Lisa Spoonhauer has been clerked Clerks.");
        inputs.add(fact);*/
        FileWriter fw = new FileWriter("src/main/resources/result.ttl");
        for(Fact i : inputs) {
            RDFTriple triplet;
            triplet = TripletExtractor.extractRDFTriplets(i.toString());
            if(triplet != null){
                Set<String> resultSet = new HashSet<>();
                try {
                    resultSet.addAll(Predicate.getForIdentifier(triplet.getPredicate()).queryDBPediaFor(triplet));
                } catch ( Exception e){
                    e.printStackTrace();
                }
                if(resultSet.size() > 0){
                    boolean isTrue = false;
                    for(String result: resultSet){
                        if(triplet.getObject().equals(result)){
                            isTrue = true;
                            break;
                        }
                        if(result.contains(triplet.getObject().replaceAll("for","in")) || result.contains(triplet.getObject().replaceAll("in","for"))){
                            isTrue = true;
                            break;
                        }
                    }
                    i.setTruthfulnessValue(isTrue ? 1.0f : -1.0f);
                }
            }
            String line = FACT_URI + i.getFactId() + "> " + PROP_URI + " \"" + i.getTruthfulnessValue() + "\"^^" + TYPE + " .\n";
            fw.write(line);
        }
        fw.close();

        System.out.println("End");

    }
}
