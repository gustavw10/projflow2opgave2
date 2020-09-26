/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import dto.PersonStyleDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Gustav
 */
public class Tester {
    
    public static void main(String[] args) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
         EntityManager em = emf.createEntityManager();
         
         Person p1 = new Person("Eko1n", 1995);
         Person p2 = new Person("Eko2n", 1997);
         
         Address a1 = new Address("Eko Street 1", 2800, "Eko Holm");
         Address a2 = new Address("Eko Street 2", 2300, "Eko Store");
         
         p1.setAddress(a1);
         p2.setAddress(a2);
         
         Fee f1 = new Fee(100);
         Fee f2 = new Fee(200);
         Fee f3 = new Fee(300);
         
         p1.addFee(f1);
         p1.addFee(f3);
         p2.addFee(f2);
         
         SwimStyle s1 = new SwimStyle("Crawl");
         SwimStyle s2 = new SwimStyle("Butterfly");
         SwimStyle s3 = new SwimStyle("Breaststroke");
         
         p1.addSwimStyle(s1);
         p1.addSwimStyle(s2);
         p2.addSwimStyle(s2);
         p2.addSwimStyle(s3);

         em.getTransaction().begin(); 
//            em.persist(a1);
//            em.persist(a2);
            em.persist(p1);
            em.persist(p2);
         em.getTransaction().commit();
         
         em.getTransaction().begin(); 
            p1.removeSwimStyle(s2);
         em.getTransaction().commit();
         
         System.out.println(p1.getId() +" "+ p1.getName() );
         System.out.println(p2.getId() +" "+ p2.getName() );
         System.out.println(p2.getAddress().getCity());
         
         System.out.println(a2.getPerson().getName());
         for(Fee f : p1.getFees()){
             System.out.println(f.getAmount());
         }
         
         System.out.println("Hvem har betalt f2 " + f2.getPerson().getName());
         
         
         Query q4 = em.createQuery("SELECT p FROM Person p");
         List<Person> personList = (List<Person>) q4.getResultList();
         System.out.println(personList.get(0).getName());
         
         Query q5 = em.createQuery("SELECT new dto.PersonStyleDTO(p.name, p.year, s.styleName) FROM Person p JOIN p.styles s");
         List<PersonStyleDTO> secondList = q5.getResultList();
         
         for(PersonStyleDTO p: secondList){
             System.out.println(p.getName() + " - " + p.getYear() + " - " + p.getSwimStyle());
         }
         
         TypedQuery<Person> qlast = em.createQuery("SELECT p FROM Person p", Person.class);
         List<Person> persons = qlast.getResultList();
         for(Person p : persons){
             System.out.println(p.getName());
             for(Fee f: p.getFees()){
                 System.out.println(f.getAmount());
                 System.out.println(f.getPayDate().toString());
             }
         }
         
    }
    
}
