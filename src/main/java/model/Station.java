package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station implements Comparable<Station> {
    private String name;
    private double thread;
    private Line line;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Double.compare(station.thread, thread) == 0 &&
                Objects.equals(name, station.name) &&
                Objects.equals(line, station.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, thread, line);
    }

    @Override
    public int compareTo(Station o) {
        return this.getName().compareTo(o.getName());
    }
}
