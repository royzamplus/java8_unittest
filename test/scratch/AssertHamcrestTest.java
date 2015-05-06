package scratch;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.number.IsCloseTo.*;

/**
 * Created by liudi on 5/6/15.
 */
public class AssertHamcrestTest {

    @Ignore
    @ExpectToFail
    @Test
    public void assertDoublesEqual() {
        assertThat(2.32 * 3, equalTo(6.96));
    }

    @Test
    public void assertWithTolerance() {
        assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
    }

    @Test
    public void assertDoublesCloseTo() {
        // from hamcrest-all lib
        assertThat(2.32 * 3, closeTo(6.96, 0.0005));
    }
}
