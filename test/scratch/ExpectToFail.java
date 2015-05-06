package scratch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liudi on 5/6/15.
 */

@Retention(RetentionPolicy.RUNTIME)
 @Target({ElementType.METHOD})

public @interface ExpectToFail {
}
