package repository;

import model.Line;
import model.Station;

import java.io.*;
import java.util.*;

public class Metro {
    private final Map<Line, ArrayList<Station>> lines = new LinkedHashMap<>();

    public Metro(String name) throws IOException {
        load(name);
    }

    /**
     * 1.	load
     * Произвести сохранение объектов из .csv файла в соответствующие коллекции линий и станций,
     * таким образом, чтобы станции были сгруппированы по линиям
     */
    private void load(String name) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader(name))) {
            while (bf.ready()) {
                try {
                    String s = bf.readLine();
                    String[] split = s.split(";");
                    Line line = new Line(split[0], split[1]);
                    Station station = new Station(split[2], Double.parseDouble(split[3]), line);

                    ArrayList<Station> lineList = lines.getOrDefault(line, new ArrayList<>());
                    lineList.add(station);
                    lines.put(line, lineList);
                } catch (RuntimeException ignored) {
                }
            }
        }
    }

    /**
     * 2.maxTraffic
     * Реализовать метод, который возвращает станции с максимальным пассажиропотоком
     */
    public ArrayList<Station> maxTraffic() {
        double maxTraffic = Double.MIN_VALUE;
        for (ArrayList<Station> value : lines.values()) {
            for (Station station : value) {
                if (station.getThread() > maxTraffic) {
                    maxTraffic = station.getThread();
                }
            }
        }
        ArrayList<Station> stations = new ArrayList<>();
        for (var value: lines.values()) {
            for (Station station : value) {
                if (station.getThread() == maxTraffic) {
                    stations.add(station);
                }
            }
        }
        return stations;
     }

    /**
     * 3.sumTraffic
     * Вычислить суммарный пассажиропоток каждой линии
     */
    public Map<Line, Double> sumTraffic() {
        Map<Line, Double> sumTraffic = new LinkedHashMap<>();
        for (Line line : lines.keySet()) {
            double sum = 0;
            for (int j = 0; j < lines.values().size(); j++) {
                sum += lines.get(line).get(j).getThread();
            }
            sumTraffic.put(line, sum);
        }
        return sumTraffic;
    }

    /**
     * 4.sortByStationName
     * Произвести сортировку коллекции по названию станции
     */
    private ArrayList<Station> sortByStationName() {
        ArrayList<Station> stations = new ArrayList<>();
        for (var entry : lines.entrySet()) {
            stations.addAll(entry.getValue());
        }
        Collections.sort(stations);
        return stations;
    }

    /**
     * 5.save
     * Вывести отсортированную коллекцию в файл .csv в таком формате же, что и исходный файл
     */
    public void save(String fileName) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(toCSV());
            bufferedWriter.newLine();
        }
    }

    private String toCSV() {
        ArrayList<Station> stations = sortByStationName();
        StringBuilder stringBuilder = new StringBuilder();
        for (var value : stations) {
            stringBuilder.append(value.getLine().getName()).append(";");
            stringBuilder.append(value.getLine().getColor()).append(";");
            stringBuilder.append(value.getName()).append(";");
            stringBuilder.append(value.getThread()).append("\r\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        String s = "";
        for (ArrayList<Station> value : lines.values()) {
            for (Station station : value) {
                s += station.getLine().getName() + " " + station.getLine().getColor() + " "
                        + station.getName() + " " + station.getThread() + "\n";
            }
        }
        return s;
    }
}
