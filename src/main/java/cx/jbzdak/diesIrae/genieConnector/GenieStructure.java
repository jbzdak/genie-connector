package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.Structure;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-11-19
 * Time: 12:57:12
 */
public interface GenieStructure {

   short getStructureId();

   Structure getStructure();

   short getRecord();

   short getEntry();

   short getSize();
}
