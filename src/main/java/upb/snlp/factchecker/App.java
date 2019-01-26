package upb.snlp.factchecker;

import upb.snlp.factchecker.bean.Fact;
import upb.snlp.factchecker.bean.RDFTriple;
import upb.snlp.factchecker.core.TripletExtractor;
import upb.snlp.factchecker.util.TSVReader;

import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Fact> inputs = TSVReader.getFactsFromFile("src/main/resources/train.tsv");
        for(Fact i : inputs) {
            RDFTriple triplet;
            try {
                triplet = TripletExtractor.extractRDFTriplets(i.toString());
                triplet.toString();
                System.out.println(i.toString() + "    --->    " + triplet.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(i.toString());
            }

        }
    }
}
