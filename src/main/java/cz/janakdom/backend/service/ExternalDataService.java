package cz.janakdom.backend.service;

import com.google.common.collect.Lists;
import cz.janakdom.backend.model.external.AttackedEntityExternal;
import cz.janakdom.backend.model.external.RailroadExternal;
import cz.janakdom.backend.model.external.TypeOfInterventionExternal;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service(value = "externalData")
public class ExternalDataService {

    private final RestTemplate restTemplate;

    public ExternalDataService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<AttackedEntityExternal> getAffectedEntities() {
        String url = "https://nnpro-exp-api.herokuapp.com/api/serie/affected-entity";
        AttackedEntityExternal[] data = this.restTemplate.getForObject(url, AttackedEntityExternal[].class);
        if (data == null)
            return new ArrayList<>();
        return Lists.newArrayList(data);
    }

    public List<TypeOfInterventionExternal> getTypeOfIntervention() {
        String url = "https://nnpro-exp-api.herokuapp.com/api/serie/type-of-intervention";
        TypeOfInterventionExternal[] data = this.restTemplate.getForObject(url, TypeOfInterventionExternal[].class);
        if (data == null)
            return new ArrayList<>();
        return Lists.newArrayList(data);
    }

    public List<RailroadExternal> getRailroads() {
        String url = "https://nnpro-exp-api.herokuapp.com/api/serie/railroads";
        RailroadExternal[] data = this.restTemplate.getForObject(url, RailroadExternal[].class);
        if (data == null)
            return new ArrayList<>();
        return Lists.newArrayList(data);
    }
}
