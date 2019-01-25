package upb.snlp.factchecker.bean;

import org.apache.xpath.operations.String;

public class RDFTriple {

    private String subject;
    private String object;
    private String predicate;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }
}
