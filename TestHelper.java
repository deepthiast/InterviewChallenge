package provided;

import java.util.Objects;

public class TestHelper {
  private TestHelper() {
  }

  public static <T> void EXPECT_EQ(T expected, T value) {
    if (!Objects.equals(expected, value)) {
      System.err.println("Fail: " + Thread.currentThread().getStackTrace()[2] + "\n\tExpected: " + expected
          + "\n\tActual  : " + value);
    }
  }
}