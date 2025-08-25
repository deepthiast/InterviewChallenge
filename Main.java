import provided.Database;
import static provided.TestHelper.*;
import provided.Road;

import java.util.*;


class Main {

  static int getLength(Database db,
                       String id1, int location1,
                       String id2, int location2) {
    

    Optional<Road> startOpt = db.getById(id1);
    Optional<Road> endOpt = db.getById(id2);
    
    if(startOpt.isEmpty() || endOpt.isEmpty()){
      return -1;
    }

    Road start = startOpt.get();
    Road end = endOpt.get();

    if(start.getLength()< location1 || end.getLength()<location2){
      return -1;
    }
    // edge Case
    if(start.getId().equals(end.getId())){
      return Math.abs(location2- location1);
    }

   
    Set<String> visited = new HashSet<>();
    return dfs(db, start, end, location1, location2, visited, 0);
  }

  private static int dfs(Database db , Road curr, Road target, int currLoc , int targetLoc, Set<String> visited, int dist){
    if(curr.getId().equals(target.getId())){
      return dist + Math.abs(targetLoc-currLoc);
    }
    visited.add(curr.getId());
    for(String conn: new String[] {"Begin", "End"}){
      Optional<String> neighborId = curr.getConnection(conn);
      if(neighborId.isPresent() && !visited.contains(neighborId.get())){
        Optional<Road> neighbor = db.getById(neighborId.get());
        if(neighbor.isPresent()){
          int result=0;
          if(conn == "End"){
            int distToNeigh = curr.getLength()-currLoc;
            result = dfs(db, neighbor.get(), target, 0, targetLoc, visited, dist +distToNeigh);

          } 
          if(conn =="Begin"){
            int distToNeigh = currLoc;
            result = dfs(db, neighbor.get(), target, neighbor.get().getLength(), targetLoc, visited, dist +distToNeigh);

          }
          if(result>=0){
            return result;
          }
        }
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    Database db = new Database();

    EXPECT_EQ(99676, getLength(db, "B", 0, "P", 3457));
    EXPECT_EQ(99676, getLength(db, "P", 3457, "B", 0));
    EXPECT_EQ(50000, getLength(db, "A", 5000, "K", 11542));
    EXPECT_EQ(50000, getLength(db, "K", 11542, "A", 5000));
    EXPECT_EQ(100, getLength(db, "C", 100, "C", 200));
    EXPECT_EQ(100, getLength(db, "C", 200, "C", 100));
    // range checks
    EXPECT_EQ(-1, getLength(db, "C", 99999, "K", 100));
    EXPECT_EQ(-1, getLength(db, "C", 100, "K", 99999));
    EXPECT_EQ(-1, getLength(db, "K", 99999, "C", 100));
    EXPECT_EQ(-1, getLength(db, "K", 100, "C", 99999));
    // invalid items
    EXPECT_EQ(-1, getLength(db, "UNKNOWN", 0, "A", 100));
    EXPECT_EQ(-1, getLength(db, "A", 100, "UNKNOWN", 0));

  }

}
