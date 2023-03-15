package objectOrientationModeling;

import objectOrientationModeling.Member;

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


        try{

            Team team = new Team();
            team.setTeamName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("heechan");
            member.setTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());

            // 이전에는 findMember.getTeamId를 이용해서 또 find 해줘야했지만
            // 이제는 findMember.getTeam()으로 바로 사용 가능!
            Team findTeam = findMember.getTeam();


            // 연관관계 수정
            // 이런식으로 연관관계를 수정할 수 있음.
            Team teamB = new Team();
            teamB.setTeamName("teamB");

            findMember.setTeam(teamB);


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
    }
}

