package edu.spm.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import edu.spm.model.ForestFire;

public class FileRepository {
    
    public static FileRepository INSTANCE = getInstance();
    private String readFileUrl = "src/resources/forestfires.csv";
    FileReader reader = null;
    //private List<ForestFire> forestfires = new ArrayList<>();
    Map<String, ForestFire> ffmap = new HashMap<>();

    private FileRepository() {

        try {
            reader = new FileReader(readFileUrl);
            ffmap = readFile();
        } catch (FileNotFoundException e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        } 
    }

    public Map<String,ForestFire> getForestFires() {
        return ffmap;
    }

    public static FileRepository getInstance() {
        if ( null == INSTANCE) {
           return INSTANCE =  new FileRepository();
        }
        return INSTANCE;
    }


    private List<String[]> toStringArray(List<ForestFire> emps) {
		List<String[]> records = new ArrayList<String[]>();

		// adding header record
		records.add(new String[] { "ID", "month", "day", "RH" });

		java.util.Iterator<ForestFire> it = emps.iterator();
		while (it.hasNext()) {
			ForestFire ff = it.next();
			records.add(new String[] { ff.getId(), ff.getMonth(), ff.getDay(), ff.getRh() });
		}
		return records;
	}

    public boolean write() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String fileTemp = "src/resources/temp.csv";
        String[] columns = new String[]  
                    { "ID", "month", "day", "RH" };
         CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(fileTemp) , ',', ' ', '\\', "\n");
            
            List<ForestFire> ff = ffmap.values().stream().collect(Collectors.toList());
            List<String[]> tempArray = toStringArray(ff);
            writer.writeAll(tempArray);
            reader.close();
            writer.close();
            FileUtils.delete(new File(readFileUrl));
            FileUtils.copyFile(new File(fileTemp), new File(readFileUrl));
        } catch( Exception e) {
           
            throw e;
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(reader);
        }
       
       
        return true;

    }

    public void read() {

    }

    public Map<String, ForestFire> readFile() {
        List<ForestFire> ff = new CsvToBeanBuilder(reader)
                .withType(ForestFire.class)
                .build()
                .parse();   
        
            MapUtils.populateMap(ffmap, ff, ForestFire::getId);        
         return ffmap;
       
    }

    public boolean update(ForestFire newff) {
        //readFile();
        ffmap.put(newff.getId(), newff);
        try {
            write();
        } catch (Exception e) {
            return false;
        } 
        return true;

    }

    public boolean add(ForestFire newff) {
        //readFile();
        if (ffmap.containsKey(newff.getId()))
        {
            return false;
        }                  
        try {
            ffmap.put(newff.getId(), newff);
            write();
        } catch (Exception e) {
            return false;
        } 
        return true;

    }


    public ForestFire getRecordForId(String id) {
       return ffmap.get(id);    
    }

    public Boolean delete(String id) {
       if (!ffmap.containsKey(id)) {
        return false;
       } else {
        try {
            ffmap.remove(id);
            write();
            return true;
        } catch(Exception e) {
            return false;
        }
        
       }
    }
}
