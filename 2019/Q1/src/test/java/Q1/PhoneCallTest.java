package Q1;

import org.junit.Test;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class PhoneCallTest {

  PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

  /*
    @Test
    public void exampleOfHowToUsePhoneCall() throws Exception {
      PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");
      call.start();
      waitForSeconds(150);
      call.end();
      call.charge();
    }

    private void waitForSeconds(int n) throws Exception {
      Thread.sleep(n * 1000);
    }
  */

  /**
   * "Show that peak time calls are charged correctly."
   * We'll want to refactor our code so that we can add custom times.
   * We also don't want our tests to take any time to run.
   */
  @Test
  public void peakTimeCallsAreChargedCorrectly() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    call.setStartTime(LocalTime.of(10, 00));
    call.setEndTime(LocalTime.of(10, 59));

    /* now, we don't want to interact with the billing system in this unit test.
      Furthermore, BillingSystem is a singleton class and so passing mock BillingSystem into the PhoneCall
      constructor doesn't really make sense.

      Instead, we'll use put a seam around the billing call. */
    assertEquals(call.generateBill(), 60 * 25);
  }

  /**
   * "Show that off-peak calls are charged correctly"
   * It might be nice to have some other test cases too... but i'm not going to do that.
   */
  @Test
  public void offPeakTimeCallsAreChargedCorrectly() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    call.setStartTime(LocalTime.of(8, 0));
    call.setEndTime(LocalTime.of(8, 59));

    assertEquals(call.generateBill(), 60 * 10);
  }

  /**
   * For question 1d:
   * "The phone company decides to change their pricing model, so that any call that
   * either starts, or ends, within the peak period is charged at the peak rate.
   * Update your tests and implementation to match this requirement."
   */
  @Test
  public void callsStartingInPeakTimeAreChargedPeakPrice() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    call.setStartTime(LocalTime.of(10, 00));
    call.setEndTime(LocalTime.of(19, 00));

    assertEquals(call.generateBill(), (60 * 9 + 1)  * 25);
  }

  @Test
  public void callsEndingInPeakTimeAreChargedPeakPrice() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    call.setStartTime(LocalTime.of(8, 00));
    call.setEndTime(LocalTime.of(17, 00));

    assertEquals(call.generateBill(), (60 * 9 + 1)  * 25);
  }

}