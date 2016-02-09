/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import BusinessLogicLayer.BusinessObjects.IdiomBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import Controller.SearchController;
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
import rest.lightObject.Idiom;
import rest.lightObject.Noun;
import rest.lightObject.Particle;
import rest.lightObject.SemanticNounTitle;
import rest.lightObject.SemanticVerbTitle;
import rest.lightObject.Verb;

/**
 * REST Web Service
 *
 * @author Omar
 */
@Path("/resources")
public class Resources {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Resources
     */
    public Resources() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/verb/{id}")
    public Verb getVerb(@PathParam("id") int id) {
        VerbBO vbo = (VerbBO) SearchController.getDerivedWordInfos(id, "verb");
        return RESTUtils.getLightVerb(vbo);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/verb/{id}/semanticTitles/{start}")
    public List<SemanticVerbTitle> getSemanticTitlesPageForVerb(@PathParam("id") int id, @PathParam("start") int start) {
        VerbBO vbo = (VerbBO) SearchController.getDerivedWordInfos(id, "verb");
        return RESTUtils.getSemanticTitlesPageForVerb(vbo, start);
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/noun/{id}")
    public Noun getNoun(@PathParam("id") int id) {
        NounBO nbo = (NounBO) SearchController.getDerivedWordInfos(id, "noun");
        return RESTUtils.getLightNoun(nbo);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/noun/{id}/semanticTitles/{start}")
    public List<SemanticNounTitle> getSemanticTitlesPageForNoun(@PathParam("id") int id, @PathParam("start") int start) {
        NounBO nbo = (NounBO) SearchController.getDerivedWordInfos(id, "noun");
        return RESTUtils.getSemanticTitlesPageForNoun(nbo, start);
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/particle/{id}")
    public Particle getParticle(@PathParam("id") int id) {
        ParticleBO part = (ParticleBO) SearchController.getDerivedWordInfos(id, "particle");
        return RESTUtils.getLightParticle(part);
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/idiom/{id}")
    public Idiom getIdiom(@PathParam("id") int id) {
        IdiomBO idiom = (IdiomBO) SearchController.getDerivedWordInfos(id, "idiom");
        return RESTUtils.getLightIdiom(idiom);
    }

    /**
     * Retrieves representation of an instance of rest.Resources
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Resources
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
