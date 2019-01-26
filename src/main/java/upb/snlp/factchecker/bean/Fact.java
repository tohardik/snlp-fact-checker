package upb.snlp.factchecker.bean;

import com.opencsv.bean.CsvBindByName;

public class Fact {

    @CsvBindByName(column = "FactID", required = true)
    private long factId;

    @CsvBindByName(column = "Fact_Statement", required = true)
    private String statement;

    @CsvBindByName(column = "True/False", required = true)
    private float truthfulnessValue;

    private boolean truthfulness;

    public long getFactId() {
        return factId;
    }

    public void setFactId(long factId) {
        this.factId = factId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean isTruthfulness() {
        return truthfulness;
    }

    public void setTruthfulness(boolean truthfulness) {
        this.truthfulness = truthfulness;
    }

    public float getTruthfulnessValue() {
        return truthfulnessValue;
    }

    public void setTruthfulnessValue(float truthfulnessValue) {
        this.truthfulnessValue = truthfulnessValue;
        if(truthfulnessValue == 1)
            this.truthfulness = true;
    }

    @Override
    public String toString() {
        return statement;
    }
}
