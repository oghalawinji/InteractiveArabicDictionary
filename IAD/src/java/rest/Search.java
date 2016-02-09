/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import rest.lightObject.ResultItem;
import BusinessLogicLayer.BusinessObjects.EntryBO;
import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import Controller.SearchController;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Omar
 */
@Path("/search")
public class Search {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Search
     */
    public Search() {
    }

    /**
     * Retrieves representation of an instance of service.Search
     *
     * @param rawWord
     * @return an instance of java.lang.String
     * @throws java.io.UnsupportedEncodingException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("{rawWord}")
    public List<ResultItem> getJson(@PathParam("rawWord") String rawWord) throws UnsupportedEncodingException {
        return getJson(rawWord, false);
    }

    /**
     * Retrieves representation of an instance of service.Search
     *
     * @param rawWord
     * @param isByRoot
     * @return an instance of java.lang.String
     * @throws java.io.UnsupportedEncodingException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("{rawWord}/byRoot/{isByRoot}")
    public List<ResultItem> getJson(@PathParam("rawWord") String rawWord, @PathParam("isByRoot") boolean isByRoot) throws UnsupportedEncodingException {
        List<EntryBO> entries = SearchController.searchByEntry(rawWord, isByRoot);
        List<ResultItem> results = new ArrayList<ResultItem>();
        if (entries != null) {
            for (EntryBO entry : entries) {
                if (entry instanceof VerbBO) {
                    results.add(RESTUtils.getVerbResultItem((VerbBO) entry));
                } else if (entry instanceof NounBO) {
                    results.add(RESTUtils.getNounResultItem((NounBO) entry));
                } else if (entry instanceof ParticleBO) {
                    results.add(RESTUtils.getParticleResultItem((ParticleBO) entry));
                } else if (entry instanceof IdiomBO) {
                    results.add(RESTUtils.getIdiomResultItem((IdiomBO) entry));
                }
            }
        } else {
            /* 
             Where no items are found, we suppose that a spelling mistake exists.            
             Notice that (results) could either contain a collection of search results
             or a collection of possible spelling mistake corrections
             */
            List<String> lexicalOptions = SearchController.getOptions(rawWord, isByRoot);
            for (String option : lexicalOptions) {
                results.add(RESTUtils.getOptionResultItem(option));
            }

        }
        return results;
    }

    /**
     * PUT method for updating or creating an instance of Search
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

}
