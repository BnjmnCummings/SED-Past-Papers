package Q1;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

public class PhoneCall {

  private static final long PEAK_RATE = 25;
  private static final long OFF_PEAK_RATE = 10;

  private final String caller;
  private final String callee;

  /**
   * The first source of Coupling is here.
   * This class now depends on the LocalTime class.
   * Further down, we call some methods that could affect our code if their functionality is changed.
   */
  private LocalTime startTime;
  private LocalTime endTime;

  public PhoneCall(String caller, String callee) {
    this.caller = caller;
    this.callee = callee;
  }

  public void start() {
    setStartTime(LocalTime.now());
  }

  public void end() {
    setEndTime(LocalTime.now());
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  /**
   * The second source of Coupling is here.
   * We are dependent on the BillingSystem class.
   */
  public void charge() {
    generateBill();
  }

  public long generateBill() {
    final long price = priceInPence();
    BillingSystem.getInstance().addBillItem(caller, callee, price);
    return price;
  }


  private long priceInPence() {
    if (duringPeakTime(startTime) || duringPeakTime(endTime)) {
      return duration() * PEAK_RATE;
    } else {
      return duration() * OFF_PEAK_RATE;
    }
  }

  private long duration() {
    return MINUTES.between(startTime, endTime) + 1;
  }

  private boolean duringPeakTime(LocalTime time) {
    return time.isAfter(LocalTime.of(9, 00)) && time.isBefore(LocalTime.of(18, 00));
  }
}
