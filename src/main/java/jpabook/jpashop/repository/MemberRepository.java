package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /* 이렇게 entity manager factory를 주입 해줄수 있다.
    @PersistenceUnit
    private EntityManagerFactory emf;
    */

    /*
    @PersistenceContext -> @Autowired 변경 가능
    변경 가능한 이유는 spring boot library가 가능하게 해주는 것이지 다른데에서 모두 가능한것이 아니다 이게 가능하기에
    @RequiredArgsConstructor로 변경이 가능한 것이다!
    private EntityManager em;
    */
    private final EntityManager em;


    public void save(Member member){
        em.persist(member);
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
