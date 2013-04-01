package ca.c301.t03_model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Handles Serialization and Deserialization of Objects
 */
public class SerializeHandler {
	
	/**
	 * To serialize a given object and return its byte array
	 * 
	 * @param o
	 * 				The object to serialize
	 * @return
	 * 				The byte array of the object
	 */
	public static byte[] serializeObject(Object o) { 
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
     
        try { 
          ObjectOutput out = new ObjectOutputStream(bos); 
          out.writeObject(o); 
          out.close(); 
     
          byte[] buf = bos.toByteArray(); 
     
          return buf; 
        } catch(IOException ioe) {      
          return null; 
        } 
      }
    
	/**
	 * To deserialize a given byte array into its object
	 * 
	 * @param b
	 * 				The byte array to deserialize
	 * @return
	 * 				The object which was deserialized
	 */
    public static Object deserializeObject(byte[] b) { 
        try { 
        	ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
        	Object object = in.readObject(); 
        	in.close(); 

        	return object; 
        } catch(ClassNotFoundException cnfe) { 
        	return null; 
        } catch(IOException ioe) { 
        	return null;
        }
    }
}
