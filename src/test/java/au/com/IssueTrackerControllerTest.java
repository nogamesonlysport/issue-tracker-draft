package au.com;

import au.com.controller.IssueTrackerController;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by amitsjoshi on 01/04/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(IssueTrackerController.class)
public class IssueTrackerControllerTest extends TestCase
{
}
