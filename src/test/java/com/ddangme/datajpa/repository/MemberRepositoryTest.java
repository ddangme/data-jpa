package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.entity.Member;
import com.ddangme.datajpa.entity.Team;
import com.ddangme.datajpa.dto.MemberDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired EntityManager em;

    @DisplayName("CREATE 테스트")
    @Test
    @Disabled
    void createTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
    }

    @DisplayName("FIND ALL 테스트")
    @Test
    @Disabled
    void findAllTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @DisplayName("COUNT 테스트")
    @Test
    @Disabled
    void countTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("UPDATE 테스트")
    @Test
    @Disabled
    void updateTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // When
        member1.setUsername("update member1");
        member2.setUsername("update member2");

        // Then
        assertThat(member1.getUsername()).isEqualTo("update member1");
        assertThat(member2.getUsername()).isEqualTo("update member2");
    }


    @DisplayName("DELETE 테스트")
    @Test
    @Disabled
    void deleteTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // When
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        // Then
        long count = memberRepository.count();
        assertThat(count).isEqualTo(0);
    }


    @DisplayName("findByUsernameAndAgeGreaterThan TEST")
    @Test
    void findByUsernameAndAgeGreaterThan() {
        // Given
        Member member1 = new Member("member", 10);
        Member member2 = new Member("member", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // When
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("member", 15);

        // Then
        assertThat(result.get(0).getUsername()).isEqualTo("member");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }


    @DisplayName("namedQuery TEST")
    @Test
    void namedQueryTest() {
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("member1");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @DisplayName("Query TEST")
    @Test
    void queryTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<Member> result = memberRepository.findUser("member1", 10);

        // Then
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @DisplayName("username List 가져오기")
    @Test
    void findUsernameListTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<String> usernames = memberRepository.findUsernameList();

        // Then
        for (String username : usernames) {
            System.out.println(username);
        }
    }


    @DisplayName("dto List 가져오기")
    @Test
    void findMemberDtoTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        m1.setTeam(teamA);
        m2.setTeam(teamB);
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<MemberDto> findDto = memberRepository.findMemberDto();

        // Then
        for (MemberDto memberDto : findDto) {
            System.out.println(memberDto);
        }
    }

    @DisplayName("findByNames TEST")
    @Test
    void findByNamesTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<Member> byNames = memberRepository.findByNames(Arrays.asList("member1", "member2"));

        // Then
        for (Member byName : byNames) {
            System.out.println(byName);
        }
    }

    @DisplayName("return type TEST")
    @Test
    void returnTypeTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<Member> members = memberRepository.findListByUsername("member");
        Member oneMember = memberRepository.findOneByUsername("member");
        Optional<Member> optionalMember = memberRepository.findOptionalByUsername("member");

        System.out.println(members);
        System.out.println(oneMember);
        System.out.println(optionalMember);
    }


    @DisplayName("페이징 TEST")
    @Test
    void paging() {
        // Given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        int pageSize = 3;
        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "username"));

        // When
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        // DTO로 반환하는 코드
        Page<MemberDto> pageResult = page.map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));

        // Then

        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        assertThat(content.size()).isEqualTo(pageSize);
        assertThat(page.getTotalElements()).isEqualTo(totalElements);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(totalElements/pageSize + 1);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }


    @DisplayName("벌크성 수정 쿼리 TEST")
    @Test
    void bulkAgePlusTest() {
        // Given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // When
        int resultCount = memberRepository.bulkAgePlus(20);

        // Then
        assertThat(resultCount).isEqualTo(3);
    }

    @DisplayName("Lazy TEST")
    @Test
    void findMemberLazyTest() {
        // Given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // When
        List<Member> members = memberRepository.findAll();

        // Then
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println(member.getTeam().getName());
        }
    }


    @DisplayName("findMemberFetchJoin TEST")
    @Test
    void findMemberFetchJoinTest() {
        // Given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // When
        List<Member> members = memberRepository.findMemberFetchJoin();

        // Then
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println(member.getTeam().getName());
        }
    }

    @DisplayName("EntityGraph TEST")
    @Test
    void entityGraphTest() {
        // Given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // When
        List<Member> members = memberRepository.findMemberEntityGraph();

        // Then
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println(member.getTeam().getName());
        }
    }


    @DisplayName("QueryHints TEST")
    @Test
    void QueryHintsTEst() {
        // Given
        memberRepository.save(new Member("member1"));

        em.flush();
        em.clear();

        // When
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2222");

        em.flush();
        em.clear();

        // Then
        Member findMember2 = memberRepository.findReadOnlyByUsername("member2222");
        assertThat(findMember2).isNull();
    }

    @DisplayName("rock TEST")
    @Test
    void rockTest() {
        // Given
        memberRepository.save(new Member("member1"));

        em.flush();
        em.clear();

        // When
        Member findMember = memberRepository.findLockByUsername("member1");


        // Then
    }

    @DisplayName("callCustom TEST")
    @Test
    void callCustomTest() {
        // Given
        List<Member> result = memberRepository.findMemberCustom();
        // When

        // Then
    }

}