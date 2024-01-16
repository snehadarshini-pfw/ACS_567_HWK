package edu.spm;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import edu.spm.model.ForestFire;
import edu.spm.repository.FileRepository;
import edu.spm.userinterface.ConsoleHandler;

/**
 * Hello world!
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
             "2 . Edit items to the file \n " +
             "3 . Add items to the file \n " +
             "4 . Delete items to the file ");
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
                case "3":
                    ConsoleHandler.printMessage("Enter id month day RH separated by space like: 31 jan mon 54");
                    newff = ConsoleHandler.readNewInput();
                    result = FileRepository.INSTANCE.add(newff);
                    if (result) {
                        ConsoleHandler.printMessage("New record added");
                    } else {
                        ConsoleHandler.printMessage("Adding new record failed . Try different id");
                    }
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
                    

                default:
                    break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
           System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
    }
}
