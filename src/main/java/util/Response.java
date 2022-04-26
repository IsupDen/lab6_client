package util;

import data.LabWork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Response implements Serializable {

    private String response;
    private LabWork labWork;
    private ArrayList<LabWork> collection;

    public Response(String response) {this.response = response;}

    public Response(LabWork labWork) {this.labWork = labWork;}

    public Response(ArrayList<LabWork> collection) {this.collection = collection;}

    public LabWork getLabWork() {return labWork;}

    public ArrayList<LabWork> getCollection() {return collection;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (response != null)
            sb.append(response);

        if (labWork != null)
            sb.append(labWork);

        if (collection != null)
            collection.stream().sorted().forEach(sg -> sb.append(sg).append("\n"));

        return sb.toString();
    }

}
