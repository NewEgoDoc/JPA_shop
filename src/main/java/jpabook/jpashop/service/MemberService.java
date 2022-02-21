package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 이게 있어야 트랜젝션 안에서 데이터 변경이 일어나기 때문에 반드시 필요
@RequiredArgsConstructor// 필드만 가지고 생성자 생성
public class MemberService {

    /* 필드에서 autowired는 값의 변경이 불가할수 있다. 흠...
    @Autowired
    private MemberRepository memberRepository;
    */
    private final MemberRepository memberRepository;

    /* ---> RequiredArgsConstructor 어노테이션으로 모두 커버가 가능하다.
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //해당 메서드로 중복 체킹을 실행해도 멀티스레드 환경에서는 동시 가입이 가능
        //따라서 DB에서도 조치가 필요한데 이름은 unique 제약 조건을 걸어주는것을 권장
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원 입니다");
    }

    /**
     * 회원 목록 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
