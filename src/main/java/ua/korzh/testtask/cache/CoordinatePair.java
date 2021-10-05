package ua.korzh.testtask.cache;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CoordinatePair {

    private final Double lat;
    private final Double lon;

    @Override
    public String toString() {

        return String.valueOf(hashCode());
    }
}
