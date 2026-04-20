package model.vestigingen;

import java.util.List;

import model.enums.Province;

public class ProvincieVestigingen {
    private Province provincie;
    private List<Vestiging> vestigingen;

    public ProvincieVestigingen(Province provincie, List<Vestiging> vestigingen) {
        this.provincie = provincie;
        this.vestigingen = vestigingen;
    }

    public Province getProvincie() {
        return provincie;
    }

    public List<Vestiging> getVestigingen() {
        return vestigingen;
    }
}