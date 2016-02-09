/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class ListReader {

      public static List<String> read(String fileName) {
        return read( fileName , "CP1256");
      }

      public static List<String> read(File file) {
        return read( file , "CP1256");
      }


      public static List<String> read(String fileName, String code) {
        return ListReader.read( new File( fileName) , code);
      }

    public static List<String> read(File file, String code) {
        FileInputStream in = null;
        List<String> tokenizedLine = new ArrayList<String>();
        try {
            in = new FileInputStream(file);
            InputStreamReader str = new InputStreamReader(in, code);
            BufferedReader br = new BufferedReader(str);
            // read in the text a line at a time
            String line = "";
            StringBuffer word = new StringBuffer();
            Character character;
            while ((line = br.readLine()) != null) {
                line = line + " ";
                // tokenize each line
                for (int i = 0; i < line.length(); i++) {
                    // if the character is not a space, append it to a word
                    if (!(character = new Character(line.charAt(i))).isWhitespace(line.charAt(i))) {
                        word.append(line.charAt(i));
                    } else {
                        if (word.length() != 0) {
                            tokenizedLine.add(word.toString());
                            word.setLength(0);
                        }
                    }
                }
            }
            // close the FileReader
            br.close();
            in.close();
        } catch (Exception ex) {
            Logger.getLogger(ListReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("---------------File not found-----------------");
        }
        return tokenizedLine;
  }
}
