package cz.upce.nnpro.cd.model.database;

import cz.upce.nnpro.cd.model.dto.NewIncident;

import javax.persistence.*;

@Entity(name = "incident")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public Incident() {

    }

    public Incident(String name) {
        this.name = name;
    }

    public Incident(NewIncident newIncident) {
        this.name = newIncident.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
