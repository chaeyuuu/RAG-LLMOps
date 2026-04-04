package fisa.techseminar2.repository.core;

import fisa.techseminar2.domain.core.CoreMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoreMemberRepository extends JpaRepository<CoreMember, Long> {
    List<CoreMember> findByIsTransferredFalse();  // 아직 이관 안 된 데이터
}