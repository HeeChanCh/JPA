package practiceExample2;

import javax.persistence.EntityManager;
        import javax.persistence.EntityManagerFactory;
        import javax.persistence.EntityTransaction;
        import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args){
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("practiceExample2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx =  em.getTransaction();
        tx.begin();


        try{
            Order order = new Order();
            order.addOrderItem(new OrderItem());



            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
