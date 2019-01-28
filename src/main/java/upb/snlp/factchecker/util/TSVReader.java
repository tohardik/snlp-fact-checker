package upb.snlp.factchecker.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import upb.snlp.factchecker.bean.Fact;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Author:  Nandeesh Patel
 */
public class TSVReader {
    /**
     *
     * @param filepath path of the file containing the list of facts.
     * @return
     *
     * This function reads the contents of the file and returns the list of facts.
     */
    public static List<Fact> getFactsFromFile(String filepath) {
        List<Fact> facts = null;

        try {
            facts = new CsvToBeanBuilder<Fact>(new FileReader(filepath))
                    .withSeparator('\t').withType(Fact.class).withFilter(new StateFilter()).build().parse();
        } catch ( FileNotFoundException exception){
            exception.printStackTrace();
        }
        return facts;
    }
}

/**
 * Implementation of filter to ignore empty lines in the file.
 */
class StateFilter implements CsvToBeanFilter {
    @Override
    public boolean allowLine(String[] strings) {
        return !strings[0].trim().isEmpty();
    }
}