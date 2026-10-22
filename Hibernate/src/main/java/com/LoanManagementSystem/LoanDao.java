package com.LoanManagementSystem;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LoanDao{

    private boolean isValid(Loan loan) {
        if (loan.getCreditScore() < 300 || loan.getCreditScore() > 900) {
            System.out.println("Rejected: credit score must be between 300 and 900.");
            return false;
        }
        if (loan.getLoanAmount() <= 0) {
            System.out.println("Rejected: loan amount must be greater than zero.");
            return false;
        }
        return true;
    }

    private boolean isDuplicate(Session session, Loan loan) {
        Long count = session.createQuery(
                "select count(l) from Loan l where l.customerName = :name "
                        + "and l.loanType = :type and l.loanAmount = :amount", Long.class)
                .setParameter("name", loan.getCustomerName())
                .setParameter("type", loan.getLoanType())
                .setParameter("amount", loan.getLoanAmount())
                .uniqueResult();
        return count != null && count > 0;
    }

    // Task 1: CREATE
    public Integer saveLoan(Loan loan) {
        if (!isValid(loan)) return null;

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (isDuplicate(session, loan)) {
                System.out.println("Rejected: duplicate application for " + loan.getCustomerName()
                        + " (" + loan.getLoanType() + ", " + loan.getLoanAmount() + ").");
                return null;
            }
            tx = session.beginTransaction();
            session.persist(loan);
            tx.commit();
            System.out.println("Loan saved with ID: " + loan.getLoanId());
            return loan.getLoanId();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error saving loan: " + e.getMessage());
            return null;
        }
    }

    // Task 2: READ ONE
    public Loan getLoanById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Loan loan = session.get(Loan.class, id);
            if (loan == null) {
                System.out.println("No loan found with ID " + id);
            } else {
                System.out.println("Customer: " + loan.getCustomerName()
                        + " | Amount: " + loan.getLoanAmount()
                        + " | Rate: " + loan.getIntrestRate() + "%"
                        + " | Duration: " + loan.getDurationMonth() + " months"
                        + " | Status: " + loan.getLoanStatus());
            }
            return loan;
        } catch (Exception e) {
            System.err.println("Error reading loan: " + e.getMessage());
            return null;
        }
    }

    // Task 3: READ ALL
    public List<Loan> getAllLoans() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Loan> loans = session.createQuery("from Loan", Loan.class).list();
            printLoans(loans, "No loan records found.");
            return loans;
        } catch (Exception e) {
            System.err.println("Error reading loans: " + e.getMessage());
            return List.of();
        }
    }

    // Task 4: UPDATE (status; APPROVED needs credit score >= 700)
    public boolean updateLoanStatus(int id, String newStatus) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Loan loan = session.get(Loan.class, id);
            if (loan == null) {
                System.out.println("No loan found with ID " + id);
                tx.rollback();
                return false;
            }
            if ("APPROVED".equals(newStatus) && loan.getCreditScore() < 700) {
                System.out.println("Cannot approve: credit score " + loan.getCreditScore() + " is below 700.");
                tx.rollback();
                return false;
            }
            loan.setLoanStatus(newStatus);
            tx.commit();
            System.out.println("Loan " + id + " status updated to " + newStatus);
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error updating status: " + e.getMessage());
            return false;
        }
    }

    // Task 4 challenge: update interest rate
    public boolean updateInterestRate(int id, double newRate) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Loan loan = session.get(Loan.class, id);
            if (loan == null) {
                System.out.println("No loan found with ID " + id);
                tx.rollback();
                return false;
            }
            loan.setIntrestRate(newRate);
            tx.commit();
            System.out.println("Loan " + id + " interest rate updated to " + newRate + "%");
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error updating interest rate: " + e.getMessage());
            return false;
        }
    }

    public boolean increaseInterestRate(int id, double increaseBy) {
        Loan existing = getLoanById(id);
        return existing != null && updateInterestRate(id, existing.getIntrestRate() + increaseBy);
    }

    // Task 5: DELETE
    public boolean deleteLoan(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Loan loan = session.get(Loan.class, id);
            if (loan == null) {
                System.out.println("No loan found with ID " + id + " - nothing deleted.");
                tx.rollback();
                return false;
            }
            session.remove(loan);
            tx.commit();
            System.out.println("Loan " + id + " deleted.");
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error deleting loan: " + e.getMessage());
            return false;
        }
    }

    // HQL: search by status
    public List<Loan> getLoansByStatus(String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Loan> loans = session.createQuery(
                    "from Loan l where l.loanStatus = :status", Loan.class)
                    .setParameter("status", status).list();
            printLoans(loans, "No loans with status " + status);
            return loans;
        }
    }

    // HQL: high-value loans
    public List<Loan> getHighValueLoans(double threshold) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Loan> loans = session.createQuery(
                    "from Loan l where l.loanAmount > :amt order by l.loanAmount desc", Loan.class)
                    .setParameter("amt", threshold).list();
            printLoans(loans, "No loans above " + threshold);
            return loans;
        }
    }

    // Transaction rollback demo
    public void simulateRollback() {
        long before = countLoans();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(new Loan(0, "Rollback Test", "CAR", 400000, 9.0, 24, "PENDING", 720));
                session.flush();
                throw new RuntimeException("Simulated failure before commit!");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Caught: " + e.getMessage() + " -> transaction rolled back.");
            }
        }

        long after = countLoans();
        System.out.println("Row count before=" + before + ", after=" + after
                + (before == after ? "  rollback verified" : "  rollback FAILED"));
    }

    private long countLoans() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(l) from Loan l", Long.class).uniqueResult();
        }
    }

    private void printLoans(List<Loan> loans, String emptyMessage) {
        if (loans.isEmpty()) {
            System.out.println(emptyMessage);
        } else {
            loans.forEach(l -> System.out.println(
                    "ID: " + l.getLoanId() + " | " + l.getCustomerName()
                            + " | " + l.getLoanAmount() + " | " + l.getLoanStatus()));
        }
    }
}