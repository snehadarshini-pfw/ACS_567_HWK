package edu.spm;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.interfaces.DHPrivateKey;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import edu.spm.model.ForestFire;
import edu.spm.repository.FileRepository;
import edu.spm.service.Dataprocess;
import edu.spm.userinterface.ConsoleHandler;

/**
 * Main application
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        FileRepository instance = FileRepository.getInstance();
        try {
            printMessage( "Welcome to the console application! Choose the menu from the following options \n" +
             "1 . Read the File \n" + 
             "2 . Edit items to the file \n" +
             "3 . Add items to the file \n" +
             "4 . Delete items to the file\n" +
             "5 . Analysis of the data\n" +
             "6 . Filter data\n");
            String menuOption = readMenuOption();
            switch (menuOption) {
                case "1":
                    Map<String, ForestFire> forestFires = instance.getForestFires();
                    displayData(forestFires);
                    break;
                case "2": 

                    printMessage("Enter the ID for the record you want to edit : ");
                    String id = readId();
                    ForestFire existingForestFires = instance.getRecordForId(id);
                    printMessage("This is the existing record for the id: " + id);
                    displayData(existingForestFires);
                    printMessage("Enter the updated record in the order Month  Day  RH for the id: " + id + " and press enter");
                    printMessage("Example : jan sun 25 ");
                    ForestFire newff = readForestFireInput(existingForestFires);
                    Boolean result = FileRepository.INSTANCE.update(newff);
                    if (result) {
                        printMessage("Update sucessful for id: " + id);
                    } else {
                        printMessage("Update Failed for id: " + id);
                    }
                    break;
                case "3":
                    printMessage("Enter id month day RH separated by space like: 31 jan mon 54");
                    newff = readNewInput();
                    result = FileRepository.INSTANCE.add(newff);
                    if (result) {
                        printMessage("New record added");
                    } else {
                        printMessage("Adding new record failed . Try different id");
                    }
                    break;
                 case "4":
                    printMessage("Enter id of the record which should be deleted");
                    id = readId();
                    existingForestFires = instance.getRecordForId(id);
                    printMessage("This is the existing record for the id: " + id);
                    displayData(existingForestFires);
                    printMessage("Are you sure you want to delete this? : Reply Y / N");
                    String option = readSingleInput();
                    if (option.equalsIgnoreCase("Y")) {
                        result = FileRepository.INSTANCE.delete(id);
                        if (result) {
                            printMessage("Deleted record with id: " + id);
                        } else {
                            printMessage("Deleted record failed. Try different id");
                        }

                    } else {
                        printMessage("Delete operation terminated");
                        
                    }
                    break;
                case "5": 
                   Pair<Double, Double> stats =  Dataprocess.getAnalysis(instance.getForestFires());
                   printMessage("Mean of RH: " + stats.getLeft());
                   printMessage("Median of RH : " + stats.getRight());

                    break;

                case "6":
                    printMessage("On which fields do you want to filter the data?");
                    printMessage("1. RH \n " +                
                    "2. Month \n" );
                    String filterOption = readMenuOption();
                    if (filterOption.equalsIgnoreCase("1")) {
                        printMessage("Choose the operation: \n 1. > \n" + "2. < \n" );
                        String operation = readMenuOption();
                        printMessage("Enter " + " filter value: ( Should numeric)" );
                        String filterValue = readMenuOption();
                        try {
                            List<ForestFire> ff = Dataprocess.filterByRh(operation, filterValue, instance.getForestFires());
                            displayData(ff);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            printMessage("Invalid Option" );
                        }
                    } else if (filterOption.equalsIgnoreCase("2")) {
                        printMessage("Enter month for filtering value" );
                        String month = readMenuOption();
                        List<ForestFire> ff = Dataprocess.filterByMonth(month, instance.getForestFires());
                        if (ff.size() == 0) {
                            printMessage("There are no values to display " );
                        }
                        displayData(ff);
                    } else {
                        printMessage("Invalid Option" );
                    }
                    break;
                default:
                    printMessage("Invalid Option" );
                    break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
           System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
    }


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
