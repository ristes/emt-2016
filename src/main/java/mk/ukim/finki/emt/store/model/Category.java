package mk.ukim.finki.emt.store.model;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Indexed
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Analyzer(definition = "emtAnalyser")
    public String name;
}
