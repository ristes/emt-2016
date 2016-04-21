package mk.ukim.finki.emt.store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

/**
 * Created by slobodanka on 2/17/2016.
 */
@Entity
@Table(name = "products")
@Indexed
@AnalyzerDef(name = "emtAnalyser",
  tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
  filters = {
    @TokenFilterDef(factory = LowerCaseFilterFactory.class)
  })
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
  @Analyzer(definition = "emtAnalyser")
  @Boost(2f)
  public String name;

  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
  @Analyzer(definition = "emtAnalyser")
  @Boost(1.5f)
  @Lob
  public String metadata;

  @Column(length = 5000)
  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
  @Analyzer(definition = "emtAnalyser")
  @Boost(1f)
  public String description;

  @ManyToOne
  @IndexedEmbedded
  public Category category;

  @JsonIgnore
  public Blob picture;

  public double price;
}
