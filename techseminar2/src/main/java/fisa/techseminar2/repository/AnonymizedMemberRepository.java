package fisa.techseminar2.repository;

import fisa.techseminar2.domain.member.AnonymizedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymizedMemberRepository extends JpaRepository<AnonymizedMember, Long> {
    boolean existsByAnonymizedId(String anonymizedId);
}
