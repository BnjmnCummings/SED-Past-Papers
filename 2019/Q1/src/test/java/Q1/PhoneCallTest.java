package Q1;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import java.time.LocalTime;

public class PhoneCallTest {

  private final String CALLER = "+447770123456";
  private final String CALLEE = "+4479341554433";

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  ChargingSystem chargingSystem = context.mock(ChargingSystem.class);
  TestClock testClock = new TestClock();
  PhoneCall call = new PhoneCall(
          CALLER,
          CALLEE,
          testClock,
          chargingSystem
  );

  /**
   * "Show that peak time calls are charged correctly."
   * We'll want to refactor our code so that we can add custom times.
   * We also don't want our tests to take any time to run.
   */
  @Test
  public void peakTimeCallsAreChargedCorrectly() throws Exception {
    context.checking(new Expectations() {{
      exactly(1).of(chargingSystem).addBillItem(CALLER, CALLEE, 60 * 25);
    }});
    testClock.setTime(LocalTime.of(10, 00));
    call.start();
    testClock.setTime(LocalTime.of(10, 59));
    call.end();

    call.charge();

  }

  /**
   * "Show that off-peak calls are charged correctly"
   * It might be nice to have some other test cases too... but i'm not going to do that.
   */
  @Test
  public void offPeakTimeCallsAreChargedCorrectly() throws Exception {
    context.checking(new Expectations() {{
      exactly(1).of(chargingSystem).addBillItem(CALLER, CALLEE, 60 * 10);
    }});

    testClock.setTime(LocalTime.of(8, 0));
    call.start();
    testClock.setTime(LocalTime.of(8, 59));
    call.end();

    call.charge();
  }

  /**
   * For question 1d:
   * "The phone company decides to change their pricing model, so that any call that
   * either starts, or ends, within the peak period is charged at the peak rate.
   * Update your tests and implementation to match this requirement."
   */
  @Test
  public void callsStartingInPeakTimeAreChargedPeakPrice() throws Exception {
    context.checking(new Expectations() {{
      exactly(1).of(chargingSystem).addBillItem(CALLER, CALLEE, (60 * 9 + 1)  * 25);
    }});

    testClock.setTime(LocalTime.of(10, 00));
    call.start();
    testClock.setTime(LocalTime.of(19, 00));
    call.end();

    call.charge();
  }

  @Test
  public void callsEndingInPeakTimeAreChargedPeakPrice() throws Exception {
    context.checking(new Expectations() {{
      exactly(1).of(chargingSystem).addBillItem(CALLER, CALLEE, (60 * 9 + 1)  * 25);
    }});

    testClock.setTime(LocalTime.of(8, 00));
    call.start();
    testClock.setTime(LocalTime.of(17, 00));
    call.end();

    call.charge();
  }

  /**
   * our class should be an idiomatic 'inner class' meaning. It doesn't need to access members/methods of the
   * enclosing class [[PhoneCallTest]] so it should be marked as static.
   */
  private static class TestClock implements Clock {

    private LocalTime time;

    public TestClock() {
      time = LocalTime.now();
    }

    public void setTime(LocalTime time) {
      this.time = time;
    }

    @Override
    public LocalTime now() {
      return time;
    }
  }

}