package cx.jbzdak.diesIrae.genieConnector;

import java.util.Timer;
import java.util.TimerTask;

class ConnectorStateWatcher extends Timer {

   public Task registerConnector(GenieConnector connector){
      Task task= new Task(connector);
      scheduleAtFixedRate(task, 0, connector.getRefreshTime());
      return task;
   }

   class Task extends TimerTask {

      private final GenieConnector genieConnector;

      private Task(GenieConnector genieConnector) {
         this.genieConnector = genieConnector;
      }

      @Override
      public void run() {
         genieConnector.updateState();
         if(genieConnector.isUpdateResults()){
            genieConnector.updateLastResult();
         }
      }
   }
}
