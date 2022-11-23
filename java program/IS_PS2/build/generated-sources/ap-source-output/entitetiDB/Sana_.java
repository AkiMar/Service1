package entitetiDB;

import entitetiDB.Racun;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-07T19:13:17")
@StaticMetamodel(Sana.class)
public class Sana_ { 

    public static volatile SingularAttribute<Sana, Date> datum;
    public static volatile SingularAttribute<Sana, Integer> brTransakcijeNa;
    public static volatile SingularAttribute<Sana, Integer> iznos;
    public static volatile SingularAttribute<Sana, String> svrha;
    public static volatile SingularAttribute<Sana, Integer> brTransakcijeSa;
    public static volatile SingularAttribute<Sana, Racun> saRac;
    public static volatile SingularAttribute<Sana, Racun> naRac;
    public static volatile SingularAttribute<Sana, Integer> idTra;

}