package parcticeExample3;

import objectOrientationModeling.Member;
import objectOrientationModeling.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain4 {

    public static void main(String[] args){
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("practiceExample3");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx =  em.getTransaction();
        tx.begin();


        try{

            Team team = new Team();
            team.setTeamName("teamA");
            em.persist(team);

            objectOrientationModeling.Member member = new Member();
            member.setName("heechan");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
