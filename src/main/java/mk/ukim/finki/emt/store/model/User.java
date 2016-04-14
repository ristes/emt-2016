package mk.ukim.finki.emt.store.model;

import javax.persistence.*;

/**
 * Created by ristes on 3/9/16.
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Column(unique = true)
  public String username;

  public String password;

  public String email;

  public boolean active = true;

  @Enumerated(EnumType.STRING)
  public Provider provider = Provider.LOCAL;

  @Enumerated(EnumType.STRING)
  public Role role;


}
