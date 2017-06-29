package gs.utils;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by seb on 6/29/17.
 */
public class TestNameLogger extends TestWatcher {

    protected void starting(Description description) {
        // TODO use LOGGER
        System.out.println("> " + description.getClassName() + "." + description.getMethodName() + "()");
    }
}
