/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class WordBO extends EntryBO implements java.io.Serializable
{

    private Integer rawWordId;
    private String rawWord;

    public WordBO()
    {
    }

    public WordBO( Integer rawWordId, String rawWord )
    {
        this.rawWordId = rawWordId;
        this.rawWord = rawWord;
    }

    public String getRawWord()
    {
        return rawWord;
    }

    public void setRawWord( String rawWord )
    {
        this.rawWord = rawWord;
    }

    public Integer getRawWordId()
    {
        return rawWordId;
    }

    public void setRawWordId( Integer rawWordId )
    {
        this.rawWordId = rawWordId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof WordBO)
        {
            WordBO newWordBO = (WordBO)obj;
            if(newWordBO.rawWord.equals( newWordBO.rawWord))
            {
                return true;
            }
        }
        return false;
    }
}
