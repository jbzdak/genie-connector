package cx.jbzdak.diesIrae.genieConnector;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-11-19
 * Time: 13:18:19
 */
public abstract class AbstractGenieStructure implements GenieStructure{

   private final short record, entry, structureId;

   protected AbstractGenieStructure(short record, short entry, short structureId) {
      this.record = record;
      this.entry = entry;
      this.structureId = structureId;
   }

   public short getRecord() {
      return record;
   }

   public short getEntry() {
      return entry;
   }

   public short getStructureId() {
      return structureId;
   }

   
}
