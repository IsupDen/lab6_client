package util;

import data.Coordinates;
import data.Difficulty;
import data.Person;

import java.io.IOException;

public interface FieldsReceiverInterface {
    String getName();
    Coordinates getCoordinates();
    Double getMinimalPoint();
    Integer getPersonalQualitiesMinimum();
    Difficulty getDifficulty();
    Person getAuthor();
}
