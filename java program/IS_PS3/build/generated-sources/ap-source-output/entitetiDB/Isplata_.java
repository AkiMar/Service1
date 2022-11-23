package entitetiDB;

import entitetiDB.Racun;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-07T18:35:16")
@StaticMetamodel(Isplata.class)
public class Isplata_ { 

    public static volatile SingularAttribute<Isplata, Date> datum;
    public static volatile SingularAttribute<Isplata, Integer> iznos;
    public static volatile SingularAttribute<Isplata, String> svrha;
    public static volatile SingularAttribute<Isplata, Integer> brTransakcije;
    public static volatile SingularAttribute<Isplata, Racun> racun;
    public static volatile SingularAttribute<Isplata, String> nazivFilijale;
    public static volatile SingularAttribute<Isplata, Integer> idTra;

}