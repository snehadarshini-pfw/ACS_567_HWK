package edu.spm.userinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import edu.spm.model.ForestFire;

public class ConsoleHandler {

    private static String readCommandLineArguments() throws IOException {

        Scanner reader = new Scanner(System.in);
        String line = reader.nextLine();
        return line;
       
    }

    private static ForestFire parseInput(String line) {
        String[] tokens = StringUtils.splitPreserveAllTokens(line, StringUtils.SPACE);
        ForestFire ff = new ForestFire(tokens[0], tokens[1], tokens[2], tokens[3]);
        return ff;
    }

    public static String readSingleInput() throws IOException {
        String line = readCommandLineArguments();
        return line;
    }
    public static String readMenuOption() throws IOException {
       return readSingleInput();
    }
    public static String readId() throws IOException {
        return readSingleInput();
    }
 
    public static void printMessage( String line) {
        System.out.println(line);
    }

    public static void displayData(Map<String,ForestFire> forestFires) {
       
        for (ForestFire forestFire : forestFires.values()) {
            displayData(forestFire);
       }
    }

    public static void displayData(List<ForestFire> forestFires) {
        
        for (ForestFire forestFire : forestFires) {
            displayData(forestFire);
       }
    }

    public static void displayData(ForestFire forestFire) {
            System.out.print("ID: " + forestFire.getId() + " | ") ;
            System.out.print("Month: " + forestFire.getMonth() + " | ");
            System.out.print("Day: " + forestFire.getDay() + " | ");
            System.out.println("RH: " + forestFire.getRh());
            System.out.println("----------------------------------------------------");
    }

    public static ForestFire readForestFireInput(ForestFire ff) throws IOException {
       String line = readSingleInput();
       String[] split = StringUtils.splitPreserveAllTokens(line);
       ff.setMonth(split[0]);
       ff.setDay(split[1]);
       ff.setRh(split[2]);
       return ff;
    }

    public static ForestFire readNewInput() throws IOException {
        String line = readSingleInput();
       String[] split = StringUtils.splitPreserveAllTokens(line);
       ForestFire ff = new ForestFire();
       ff.setId(split[0]);
       ff.setMonth(split[1]);
       ff.setDay(split[2]);
       ff.setRh(split[3]);
       return ff;
    }

    
}
