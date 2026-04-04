package fisa.techseminar2.service;

import fisa.techseminar2.domain.core.CoreMember;
import fisa.techseminar2.domain.member.AnonymizedMember;
import fisa.techseminar2.infrastructure.anonymize.AnonymizePort;
import fisa.techseminar2.repository.AnonymizedMemberRepository;
import fisa.techseminar2.repository.core.CoreMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnonymizeService {

    private final CoreMemberRepository coreMemberRepository;
    private final AnonymizedMemberRepository anonymizedMemberRepository;
    private final AnonymizePort anonymizePort;

    // 매일 새벽 2시 자동 실행
    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void transferAndAnonymize() {
        log.info("비식별화 이관 시작");

        // 아직 이관 안 된 계정계 데이터 조회
        List<CoreMember> notTransferred = coreMemberRepository.findByIsTransferredFalse();
        log.info("이관 대상: {}건", notTransferred.size());

        notTransferred.forEach(core -> {
            // 중복 체크
            String anonymizedId = UUID.nameUUIDFromBytes(
                    core.getId().toString().getBytes()
            ).toString();

            if (anonymizedMemberRepository.existsByAnonymizedId(anonymizedId)) {
                log.info("이미 이관된 데이터: {}", anonymizedId);
                return;
            }

            // k-익명성 적용
            AnonymizedMember anonymized = AnonymizedMember.builder()
                    .anonymizedId(anonymizedId)
                    .ageGroup(anonymizePort.anonymizeAge(core.getAge()))
                    .gender(core.getGender())
                    .budgetRange(anonymizePort.anonymizeIncome(core.getMonthlyBudget()))
                    .job(core.getJob())
                    .build();

            anonymizedMemberRepository.save(anonymized);
            log.info("비식별화 완료: {} → {}", core.getId(), anonymizedId);
        });

        log.info("비식별화 이관 완료: {}건", notTransferred.size());
    }

    // 수동 실행용 (테스트)
    @Transactional
    public void transferNow() {
        transferAndAnonymize();
    }
}
