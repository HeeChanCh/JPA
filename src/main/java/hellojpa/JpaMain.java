package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx =  em.getTransaction();
        tx.begin();

        // 기본
        try{
            Member member = new Member();
            member = em.find(Member.class, 1L);



            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        // 기본키 식별자 전략 - Identity
        // 원래라면 transaction commit시에 insert 쿼리가 전송됫지만,
        // Identity전략은 em.persist시에 insert 쿼리가 전송됨.
        // 그니까 오토인크리먼트로 pk 생성하고 가져오는거임~!
        try{
            MemberIdentity member = new MemberIdentity();
            member.setUsername("heechan");

            em.persist(member);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        // 기본키 식별자 전략 - SEQUENCE
        // Oracle의 Sequence
        // MemberSequence에서 지정된 SequenceGenerator 어노테이션에서 값을 가져와서 사용함
        try{
            MemberIdentity member = new MemberIdentity();
            member.setUsername("heechan");

            em.persist(member);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
