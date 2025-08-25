import provided.Database;
import static provided.TestHelper.*;

class Main {

  static int getLength(Database db,
      String id1, int location1,
      String id2, int location2) {

    // TODO: write your code here

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