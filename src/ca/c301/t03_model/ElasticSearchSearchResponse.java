package ca.c301.t03_model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles elastic search results
 */
public class ElasticSearchSearchResponse<T> {
    int took;
    boolean timed_out;
    transient Object _shards;
    Hits<T> hits;
    boolean exists;    
    
    /**
     * Gets the collection of hits from the Elastic Search
     * 
     * @return
     * 				A Collection of Elastic Search Responses
     */
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits.getHits();        
    }
    
    /**
     * Gets the sources of the Elastic Search hits
     * 
     * @return
     * 				A Collection, which are the sources
     */
    public Collection<T> getSources() {
        Collection<T> out = new ArrayList<T>();
        for (ElasticSearchResponse<T> essrt : getHits()) {
            out.add( essrt.getSource() );
        }
        return out;
    }
    
    /**
     * To turn this Elastic Search Response into a String format
     * 
     * @return
     * 				The string format
     */
    public String toString() {
        return (super.toString() + ":" + took + "," + _shards + "," + exists + ","  + hits);     
    }
}
