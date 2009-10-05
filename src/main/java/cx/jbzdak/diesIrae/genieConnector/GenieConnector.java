package cx.jbzdak.diesIrae.genieConnector;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@ThreadSafe
public class GenieConnector  extends SimpleConnector{

   private static final ConnectorStateWatcher CONNECTOR_STATE_WATCHER = new ConnectorStateWatcher();

   private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

   private ConnectorStateWatcher.Task task;

   private SpectrometricResult lastResult;

   private long refreshTime = 1000;

   private boolean updateResults = true;

   public GenieConnector() {
      task = CONNECTOR_STATE_WATCHER.registerConnector(this);
   }

   @GuardedBy("this.acquiringLock")
   private boolean acquiring;

   public long getRefreshTime() {
      return refreshTime;
   }

   public void setRefreshTime(long refreshTime) {
      this.refreshTime = refreshTime;
      task.cancel();
      task = CONNECTOR_STATE_WATCHER.registerConnector(this);
   }

   public boolean isUpdateResults() {
      return updateResults;
   }

   public void setUpdateResults(boolean updateResults) {
      this.updateResults = updateResults;
   }

   public SpectrometricResult getLastResult() {
      readWriteLock.readLock().lock();
      try{
         return lastResult;
      }finally {
         readWriteLock.readLock().unlock();
      }      
   }

   public void setLastResult(SpectrometricResult lastResult) {
      readWriteLock.writeLock().lock();
      try{
         SpectrometricResult oldLastResult = this.lastResult;
         this.lastResult = lastResult;
         support.firePropertyChange("lastResult", oldLastResult, this.lastResult);
      }finally {
         readWriteLock.writeLock().unlock();
      }
   }

   @Override
   public boolean isAcquiring() {
      readWriteLock.readLock().lock();
      try{
      return acquiring;
      }finally {
         readWriteLock.readLock().unlock();
      }
   }

   void setAcquiring(boolean acquiring) {
      readWriteLock.writeLock().lock();
      if(this.acquiring != acquiring){
         this.acquiring = acquiring;
         support.firePropertyChange("acquiring", !acquiring, acquiring);
      }
      readWriteLock.writeLock().unlock();
   }

   @Override
   protected void closeNoCheck() {
      task.cancel();
      super.closeNoCheck();
   }

   public void updateState(){
      if(getConnectorState().equals(ConnectorState.OPEN)){
         setAcquiring(super.isAcquiring());
       }
   }

   public void updateLastResult(){
      setLastResult(getSpectrometricData());
   }
}

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