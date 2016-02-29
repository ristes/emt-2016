package mk.ukim.finki.emt.store.model;

import javax.persistence.*;

/**
 * Created by slobodanka on 2/17/2016.
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Column(length = 5000)
    public String description;

    @ManyToOne
    public Category category;
}
