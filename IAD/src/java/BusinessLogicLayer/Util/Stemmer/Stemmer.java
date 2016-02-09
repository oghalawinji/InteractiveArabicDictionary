/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util.Stemmer;

import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.RawwordJPADAO;
import PersistenceLayer.Prefix;
import PersistenceLayer.Rawword;
import PersistenceLayer.Suffix;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class Stemmer {
    private List<String> prefixes = new ArrayList();
    private List<String> suffixes = new ArrayList();

   /* public Stemmer(){
        prefixes = new ArrayList();
        suffixes = new ArrayList();
    }*/

    public Stemmer( List<String> pres, List<String> sufs){
        prefixes = pres;
        suffixes = sufs;
    }

    public Stemmer(/* String prefixesFile, String suffixesFile*/){
        List<Prefix> list1 = BLUtil.daoFactory.getPrefixDAO().getAll();//ListReader.read(prefixesFile);
        prefixes = new ArrayList<String>();
        for( int i=0; i<list1.size(); i++){
            prefixes.add(list1.get(i).getPrefix());
        }

        List<Suffix> list2 = BLUtil.daoFactory.getSuffixDAO().getAll();//ListReader.read(prefixesFile);
        suffixes = new ArrayList<String>();
        for( int i=0; i<list2.size(); i++){
            suffixes.add(list2.get(i).getSuffix());
        }

        prefixes.add("");
        suffixes.add("");
    }

    public List<String> getStem( String word){
        RawwordJPADAO dao = BLUtil.daoFactory.getRawwordDAO();
        List<String> stems = new ArrayList<String>();
        List<SegmentedWord> segList = generateFragments( word);
        for( int i=0; i<segList.size(); i++){
            SegmentedWord seg = segList.get(i);
            if( ! prefixes.contains(seg.getPrefix())
              ||! suffixes.contains(seg.getSuffix())
              ||  dao.getByExample(new Rawword(seg.getStem())).isEmpty()
              )
            {
                segList.remove(i);
                i--;
            }
            else{
                stems.add(seg.getStem());
            }
        }
        return stems;
    }

	private List<SegmentedWord> generateFragments(String word) {
		List segmented = new ArrayList();
		int prefix_len = 0;
		int suffix_len = 0;
		while ((prefix_len) <= 4 && (prefix_len <= word.length())) {
			String prefix = word.substring(0, prefix_len);
			int stem_len = (word.length() - prefix_len);
			suffix_len = 0;
			while ((stem_len >= 1) && (suffix_len <= 6)) {
				String stem = word.substring(prefix_len, prefix_len + stem_len);
				String suffix = word.substring(prefix_len + stem_len, prefix_len + stem_len + suffix_len);
				segmented.add(new SegmentedWord(prefix, stem, suffix));
				stem_len--;
				suffix_len++;
			}
			prefix_len++;
		}
		return segmented;
	}
}
