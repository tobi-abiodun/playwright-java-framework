package testflow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a TestFlow method with a stable id and description.
 * Example: @TestFlow(description = "TF1: Verify the login page")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestFlow {

    String description();
}
