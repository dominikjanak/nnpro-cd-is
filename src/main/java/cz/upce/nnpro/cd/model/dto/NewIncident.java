package cz.upce.nnpro.cd.model.dto;

public class NewIncident {

    protected String name;

    public NewIncident() {

    }

    public NewIncident(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
