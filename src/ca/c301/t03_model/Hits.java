package ca.c301.t03_model;

import java.util.Collection;

/**
 * Handles elastic search hits
 */
public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    
    /**
     * Gets the Collection of Elastic Search Responses
     * 
     * @return
     * 				The Collection of Elastic Search Responses
     */
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    
    /**
     * To convert to a String representation
     * 
     * @return
     * 				The string representation
     */
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}