package fisa.techseminar2.infrastructure.anonymize;

public interface AnonymizePort {
    String anonymizeAge(int age);
    String anonymizeIncome(int monthlyBudget);
}
