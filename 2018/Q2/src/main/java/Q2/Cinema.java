package Q2;

import java.util.*;

public class Cinema {

  private final TicketIssuer ticketer = new TicketIssuer();
  private final BoxOffice boxOffice = new BoxOffice();

  public void bookTicketFor(CustomerId customer, Showing showing) {

    String ticketId = ticketer.reserveTicketFor(showing);

    /*
      > boxOffice.getCustomerDatabase().getCustomer(customer).getTickets().add(ticketId);
     * This line of code is violating the law of demeter.
     * Direct collaborators of Cinema include: TicketIssuer and BoxOffice. However, here we are trying to
     * interact with the customer database which is a 'friend of a friend'.
     */

    boxOffice.addTicket(customer, ticketId);
  }
}

class BoxOffice {

  private final CustomerDatabase customerDatabase = new CustomerDatabase();

  public void addTicket(CustomerId customer, String ticketId) {
    customerDatabase.addTicket(customer, ticketId);
  }

}

class TicketIssuer {

  public String reserveTicketFor(Showing showing) {
    // generate a ticket id for the relevant showing.
    return UUID.randomUUID().toString();
  }
}

class CustomerDatabase {

  private final Map<CustomerId, Customer> customers = new HashMap<>();

  public void addTicket(CustomerId customer, String ticketId) {
    getCustomer(customer).addTicket(ticketId);
  }
  public Customer getCustomer(CustomerId customerId) {
    return customers.get(customerId);
  }
}

class Customer {

  private final List<String> currentTickets = new ArrayList<>();

  public List<String> getTickets() {
    return currentTickets;
  }

  public void addTicket(String ticketId) {
    currentTickets.add(ticketId);
  }

}

class Showing { /* left empty for this exercise */
}

class CustomerId { /* left empty for this exercise */
}
