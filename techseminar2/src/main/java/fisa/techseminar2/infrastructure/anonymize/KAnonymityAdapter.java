package fisa.techseminar2.infrastructure.anonymize;

import org.springframework.stereotype.Component;

@Component
public class KAnonymityAdapter implements AnonymizePort {
    // 나이 → 연령대로 일반화
    @Override
    public String anonymizeAge(int age) {
        if (age < 20) return "10대";
        else if (age < 30) return "20대";
        else if (age < 40) return "30대";
        else if (age < 50) return "40대";
        else if (age < 60) return "50대";
        else return "60대 이상";
    }

    // 월 예산 → 구간으로 일반화
    @Override
    public String anonymizeIncome(int monthlyBudget) {
        if (monthlyBudget < 30000) return "3만원 미만";
        else if (monthlyBudget < 50000) return "3~5만원";
        else if (monthlyBudget < 100000) return "5~10만원";
        else return "10만원 이상";
    }
}
