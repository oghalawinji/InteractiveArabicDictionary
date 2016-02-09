/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util.Stemmer;

/**
 *
 * @author riad
 */
class SegmentedWord {
    private String prefix;
    private String stem;
    private String suffix;

    public SegmentedWord(String _prefix, String _stem, String _suffix) {
        prefix = _prefix;
        stem = _stem;
        suffix = _suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    

}
