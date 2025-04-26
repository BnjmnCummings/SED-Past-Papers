package Q1;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

public class PhoneCall {

  private static final long PEAK_RATE = 25;
  private static final long OFF_PEAK_RATE = 10;

  private final String caller;
  private final String callee;
  private final Clock clock;
  private final ChargingSystem billingSystem;

  /**
   * The first source of Coupling is here.
   * This class now depends on the LocalTime class.
   * Further down, we call some methods that could affect our code if their functionality is changed.
   */
  private LocalTime startTime;
  private LocalTime endTime;

  public PhoneCall(String caller, String callee, Clock clock, ChargingSystem billingSystem) {
    this.caller = caller;
    this.callee = callee;
    this.clock = clock;
    this.billingSystem = billingSystem;
  }

  public void start() {
    this.startTime = clock.now();
  }

  public void end() {
    this.endTime = clock.now();
  }

  public void charge() {
    billingSystem.addBillItem(caller, callee, priceInPence());
  }

  public long getPriceInPence() {
    return priceInPence();
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
