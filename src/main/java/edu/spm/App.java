package edu.spm;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.interfaces.DHPrivateKey;

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
            ConsoleHandler.printMessage( "Welcome to the console application! Choose the menu from the following options \n" +
             "1 . Read the File \n" + 
             "2 . Edit items to the file \n" +
             "3 . Add items to the file \n" +
             "4 . Delete items to the file\n" +
             "5 . Analysis of the data\n" +
             "6 . Filter data\n");
            String menuOption = ConsoleHandler.readMenuOption();
            switch (menuOption) {
                case "1":
                    Map<String, ForestFire> forestFires = instance.getForestFires();
                    ConsoleHandler.displayData(forestFires);
                    break;
                case "2": 

                    ConsoleHandler.printMessage("Enter the ID for the record you want to edit : ");
                    String id = ConsoleHandler.readId();
                    ForestFire existingForestFires = instance.getRecordForId(id);
                    ConsoleHandler.printMessage("This is the existing record for the id: " + id);
                    ConsoleHandler.displayData(existingForestFires);
                    ConsoleHandler.printMessage("Enter the updated record in the order Month  Day  RH for the id: " + id + " and press enter");
                    ConsoleHandler.printMessage("Example : jan sun 25 ");
                    ForestFire newff = ConsoleHandler.readForestFireInput(existingForestFires);
                    Boolean result = FileRepository.INSTANCE.update(newff);
                    if (result) {
                        ConsoleHandler.printMessage("Update sucessful for id: " + id);
                    } else {
                        ConsoleHandler.printMessage("Update Failed for id: " + id);
                    }
                    break;
                case "3":
                    ConsoleHandler.printMessage("Enter id month day RH separated by space like: 31 jan mon 54");
                    newff = ConsoleHandler.readNewInput();
                    result = FileRepository.INSTANCE.add(newff);
                    if (result) {
                        ConsoleHandler.printMessage("New record added");
                    } else {
                        ConsoleHandler.printMessage("Adding new record failed . Try different id");
                    }
                    break;
                 case "4":
                    ConsoleHandler.printMessage("Enter id of the record which should be deleted");
                    id = ConsoleHandler.readId();
                    existingForestFires = instance.getRecordForId(id);
                    ConsoleHandler.printMessage("This is the existing record for the id: " + id);
                    ConsoleHandler.displayData(existingForestFires);
                    ConsoleHandler.printMessage("Are you sure you want to delete this? : Reply Y / N");
                    String option = ConsoleHandler.readSingleInput();
                    if (option.equalsIgnoreCase("Y")) {
                        result = FileRepository.INSTANCE.delete(id);
                        if (result) {
                            ConsoleHandler.printMessage("Deleted record with id: " + id);
                        } else {
                            ConsoleHandler.printMessage("Deleted record failed. Try different id");
                        }

                    } else {
                        ConsoleHandler.printMessage("Delete operation terminated");
                        
                    }
                    break;
                case "5": 
                   Pair<Double, Double> stats =  Dataprocess.getAnalysis(instance.getForestFires());
                   ConsoleHandler.printMessage("Mean of RH: " + stats.getLeft());
                   ConsoleHandler.printMessage("Median of RH : " + stats.getRight());

                    break;

                case "6":
                    ConsoleHandler.printMessage("On which fields do you want to filter the data?");
                    ConsoleHandler.printMessage("1. RH \n " +                
                    "2. Month \n" );
                    String filterOption = ConsoleHandler.readMenuOption();
                    if (filterOption.equalsIgnoreCase("1")) {
                        ConsoleHandler.printMessage("Choose the operation: \n 1. > \n" + "2. < \n" );
                        String operation = ConsoleHandler.readMenuOption();
                        ConsoleHandler.printMessage("Enter " + " filter value: ( Should numeric)" );
                        String filterValue = ConsoleHandler.readMenuOption();
                        try {
                            List<ForestFire> ff = Dataprocess.filterByRh(operation, filterValue, instance.getForestFires());
                            ConsoleHandler.displayData(ff);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            ConsoleHandler.printMessage("Invalid Option" );
                        }
                    } else if (filterOption.equalsIgnoreCase("2")) {
                        ConsoleHandler.printMessage("Enter month for filtering value" );
                        String month = ConsoleHandler.readMenuOption();
                        List<ForestFire> ff = Dataprocess.filterByMonth(month, instance.getForestFires());
                        if (ff.size() == 0) {
                            ConsoleHandler.printMessage("There are no values to display " );
                        }
                        ConsoleHandler.displayData(ff);
                    } else {
                        ConsoleHandler.printMessage("Invalid Option" );
                    }
                    break;
                default:
                    ConsoleHandler.printMessage("Invalid Option" );
                    break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
           System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
    }
}
