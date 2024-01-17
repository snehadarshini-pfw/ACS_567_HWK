package edu.spm.service;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.StatUtils;

import edu.spm.model.ForestFire;

public class Dataprocess {
    

    public static Pair<Double, Double> getAnalysis(Map<String, ForestFire> map) {
       IntSummaryStatistics statistics = map.values().stream().map(ForestFire::getRh).map(Integer::valueOf).mapToInt(Integer::valueOf).summaryStatistics();
       Double mean = statistics.getAverage();
       double[] values =  map.values().stream().map(ForestFire::getRh).map(Integer::valueOf).sorted().mapToDouble(Double::valueOf).toArray();
      // double[] v = values.mapToDouble(Double::valueOf).toArray();
       
        double median =  StatUtils.percentile(values, 50.0);
        return new ImmutablePair<Double, Double>(statistics.getAverage(), median);
    }




    public static List<ForestFire> filterByRh(String operation, String filterValue, Map<String, ForestFire> ffmap) throws Exception {
        int fv = Integer.parseInt(filterValue);
        List<ForestFire> fileredList = new ArrayList();
        if (operation.equalsIgnoreCase("2")) {
           fileredList = ffmap.values().stream().filter( v -> Integer.parseInt(v.getRh()) < fv).collect(Collectors.toList());
        } else if (operation.equalsIgnoreCase("1")) {
          fileredList = ffmap.values().stream().filter(  v -> Integer.parseInt(v.getRh()) > fv).collect(Collectors.toList());
        } else {
            throw new Exception("unsupported values");
        } 
        return fileredList;
       
    }

    public static List<ForestFire> filterByMonth(String month, Map<String, ForestFire> ffmap) {
        return ffmap.values().stream().filter( v -> v.getMonth().equalsIgnoreCase(month)).collect(Collectors.toList());
    }

}
