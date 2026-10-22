package com.LoanManagementSystem;

public class Main {
    public static void main(String[] args) {
    	LoanDao dao = new LoanDao();

        try {
            System.out.println("\n=== Task 1: Create ===");
            Integer id = dao.saveLoan(new Loan(0, "Rahul Sharma", "PERSONAL", 300000, 12.0, 36, "PENDING", 750));

            System.out.println("\n=== Duplicate check ===");
            dao.saveLoan(new Loan(0, "Rahul Sharma", "PERSONAL", 300000, 12.0, 36, "PENDING", 750));

            System.out.println("\n=== Validation checks ===");
            dao.saveLoan(new Loan(0, "Bad Score", "HOME", 100000, 8.0, 12, "PENDING", 250));
            dao.saveLoan(new Loan(0, "Bad Amount", "HOME", 0, 8.0, 12, "PENDING", 650));

            dao.saveLoan(new Loan(0, "Anita Rao", "HOME", 2500000, 8.5, 240, "PENDING", 780));
            dao.saveLoan(new Loan(0, "Vikram Singh", "CAR", 450000, 9.5, 60, "PENDING", 620));

            System.out.println("\n=== Task 2: Read one ===");
            dao.getLoanById(id != null ? id : 1);
            dao.getLoanById(9999);

            System.out.println("\n=== Task 3: Read all ===");
            dao.getAllLoans();

            System.out.println("\n=== Task 4: Update ===");
            if (id != null) {
                dao.updateLoanStatus(id, "APPROVED");
                dao.updateInterestRate(id, 10.5);
                dao.increaseInterestRate(id, 0.5);
            }
            dao.getLoansByStatus("PENDING").stream()
                    .filter(l -> l.getCustomerName().equals("Vikram Singh"))
                    .findFirst().ifPresent(l -> dao.updateLoanStatus(l.getLoanId(), "APPROVED"));

            System.out.println("\n=== PENDING loans ===");
            dao.getLoansByStatus("PENDING");

            System.out.println("\n=== High-value loans ===");
            dao.getHighValueLoans(500000);

            System.out.println("\n=== Rollback demo ===");
            dao.simulateRollback();

            System.out.println("\n=== Task 5: Delete ===");
            dao.deleteLoan(9999);
            dao.getLoansByStatus("PENDING").stream()
                    .filter(l -> l.getCustomerName().equals("Anita Rao"))
                    .findFirst().ifPresent(l -> dao.deleteLoan(l.getLoanId()));

            System.out.println("\n=== Final state ===");
            dao.getAllLoans();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}