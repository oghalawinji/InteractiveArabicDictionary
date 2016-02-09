/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control;

import Controller.SearchController;
import sarfDic.Ajax.AjaxRequest;
import java.util.List;

import sarfDic.morphgen.*;

/*
import BusinessLogicLayer.BusinessObjects.*;
import Controller.*;
import BusinessLogicLayer.SearchService.SemanticSearch.*;
 */
/**
 *
 * @author Gowzancha
 */
public class MainControl
{

    public static MainControl getInstance()
    {
        return new MainControl();
    }

    public String mainController( AjaxRequest ajaxReq )
    {//String root, String conjugation, String inflection, String verbTense, String nounType, String nounPattern, String nounState) {
        // Divs
        String properties = "";
        String inflections = "";
        String vocalization = "";
        String tenses = "";
        String patterns = "";
        String body = "";
        String states = "";


        if ( rootIsValid( ajaxReq ) )
        {
            if ( ajaxReq.getConjugation().equals( "" ) )
            {// Main Interface
                List list = MainGenerator.getList( ajaxReq.getRoot() );
                // body=ajaxReq.getRoot();
                switch ( ajaxReq.getRoot().length() )
                {
                    case 3:
                        body = sarfDic.Control.Div.Body.getInstance().getMainTrilateral( list );
                        break;
                    case 4:
                        body = sarfDic.Control.Div.Body.getInstance().getMainQuadrilateral( list );
                        break;
                }
                String kovDesc = MainGenerator.getKovDesc( ajaxReq.getRoot() );
                properties = sarfDic.Control.Div.Properties.getInstance().getProperties( kovDesc , "" , "" );
                inflections = " "; // space make the interface take it and empty the div
                vocalization = " ";
                tenses = " ";
                patterns = " ";
                states = " ";
            }
            else
            {
                if ( ajaxReq.getInflection().equals( "" ) )
                { // Inflections Interface
                    inflections = sarfDic.Control.Div.Inflections.getInstance().getInflections();
                    String kovDesc = MainGenerator.getKovDesc( ajaxReq.getRoot() );
                    String transitive = MainGenerator.getTransitive( ajaxReq.getRoot() , ajaxReq.getConjugation() );
                    String verb = MainGenerator.get( Integer.parseInt( ajaxReq.getConjugation() ) , ajaxReq.getRoot() );
                    properties = sarfDic.Control.Div.Properties.getInstance().getProperties( kovDesc , transitive , verb );
                    vocalization = " ";
                    tenses = " ";
                    patterns = " ";
                    body = " ";
                    states = " ";
                }
                else
                {
                    if ( ajaxReq.getVocalization().equals( "" ) && !sarfDic.Control.Div.Vocalization.getInstance().getVocalization( ajaxReq ).equals( " " ) )
                    {
                        vocalization = sarfDic.Control.Div.Vocalization.getInstance().getVocalization( ajaxReq );
                        tenses = " ";
                        patterns = " ";
                        body = " ";
                        states = " ";
                    }
                    else
                    {
                        if ( ajaxReq.getVocalization().equals( "" ) )
                        {
                            ajaxReq.setVocalization( "false" );
                        }
                        if ( ajaxReq.getInflection().equals( "active" ) || ajaxReq.getInflection().equals( "passive" ) )
                        {// Verbs Interface
                            if ( ajaxReq.getVerbTense().equals( "" ) )
                            {
                                ajaxReq.setVerbTense( "past" );
                                tenses = sarfDic.Control.Div.TenTyp.getInstance().getVerbTenses( ajaxReq.getInflection() );
                            }
                            switch ( ajaxReq.getRoot().length() )
                            {
                                case 3:
                                    body = sarfDic.Control.Div.Body.getInstance().getVerbs( sarfDic.Control.Trilateral.verbControl.getInstance().verbController( ajaxReq ) );
                                    break;
                                case 4:
                                    body = sarfDic.Control.Div.Body.getInstance().getVerbs( sarfDic.Control.Quadrilateral.verbControl.getInstance().verbController( ajaxReq ) );
                                    break;
                            }
                            patterns = " ";
                            states = " ";
                        }
                        else
                        {
                            if ( ajaxReq.getInflection().equals( "nouns" ) )
                            {
                                if ( ajaxReq.getNounType().equals( "" ) )
                                {
                                    ajaxReq.setNounType( "activeParticiple" );
                                    tenses = sarfDic.Control.Div.TenTyp.getInstance().getNounTypes( ajaxReq );
                                }
                                if ( ajaxReq.getNounState().equals( "" ) )
                                {
                                    ajaxReq.setNounState( "indefinite" );
                                    states = sarfDic.Control.Div.States.getInstance().getStates( ajaxReq );
                                }
                                if ( ajaxReq.getNounPattern().equals( "" ) )
                                {
                                    ajaxReq.setNounPattern( "0" );
                                    if ( ajaxReq.getRoot().length() == 3 )
                                    {
                                        patterns = sarfDic.Control.Div.Patterns.getInstance().getPatterns( sarfDic.Control.Trilateral.nounControl.getInstance().getNounPatternsList( ajaxReq ) );
                                    }
                                }
                                switch ( ajaxReq.getRoot().length() )
                                {
                                    case 3:
                                        body = sarfDic.Control.Div.Body.getInstance().getNouns( sarfDic.Control.Trilateral.nounControl.getInstance().nounController( ajaxReq ) );
                                        break;
                                    case 4:
                                        body = sarfDic.Control.Div.Body.getInstance().getNouns( sarfDic.Control.Quadrilateral.nounControl.getInstance().nounController( ajaxReq ) );
                                        break;
                                }
                            }
                            else
                            {
                                if ( ajaxReq.getInflection().equals( "gerunds" ) )
                                {
                                    if ( ajaxReq.getNounType().equals( "" ) )
                                    {
                                        ajaxReq.setNounType( "standard" );
                                        tenses = sarfDic.Control.Div.TenTyp.getInstance().getGerundTypes( ajaxReq );
                                    }
                                    if ( ajaxReq.getNounState().equals( "" ) )
                                    {
                                        ajaxReq.setNounState( "indefinite" );
                                        states = sarfDic.Control.Div.States.getInstance().getStates( ajaxReq );
                                    }
                                    if ( ajaxReq.getNounPattern().equals( "" ) )
                                    {
                                        ajaxReq.setNounPattern( "0" );
                                        switch ( ajaxReq.getRoot().length() )
                                        {
                                            case 3:
                                                patterns = sarfDic.Control.Div.Patterns.getInstance().getPatterns( sarfDic.Control.Trilateral.gerundControl.getInstance().getGerundPatternsList( ajaxReq ) );
                                                break;
                                            case 4:
                                                patterns = sarfDic.Control.Div.Patterns.getInstance().getPatterns( sarfDic.Control.Quadrilateral.gerundControl.getInstance().getGerundPatternsList( ajaxReq ) );
                                                break;
                                        }
                                    }
                                    switch ( ajaxReq.getRoot().length() )
                                    {
                                        case 3:
                                            body = sarfDic.Control.Div.Body.getInstance().getNouns( sarfDic.Control.Trilateral.gerundControl.getInstance().gerundController( ajaxReq ) );
                                            break;
                                        case 4:
                                            body = sarfDic.Control.Div.Body.getInstance().getNouns( sarfDic.Control.Quadrilateral.gerundControl.getInstance().gerundController( ajaxReq ) );
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        else
        { // root is not valid
            body = sarfDic.Control.Div.Body.getInstance().rootIsNotValidResponse( ajaxReq );
            properties = " ";
            inflections = " ";
            tenses = " ";
            patterns = " ";
            states = " ";
        }
        return "({\"properties\":\"" + properties + "\", \"inflections\": \"" + inflections + "\", \"vocalization\": \"" + vocalization + "\", \"tenses\": \"" + tenses + "\", \"patterns\": \"" + patterns + "\" ,\"body\":\"" + body + "\", \"states\":\"" + states + "\"})";
    }

    public boolean rootIsValid( AjaxRequest ajaxReq )
    {
        List<String> roots = SearchController.getRoots( ajaxReq.getRoot() );
        if ( roots.size() == 1 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
