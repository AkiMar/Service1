package entitetiDB;

import entitetiDB.Racun;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-07T18:35:16")
@StaticMetamodel(Uplata.class)
public class Uplata_ { 

    public static volatile SingularAttribute<Uplata, Date> datum;
    public static volatile SingularAttribute<Uplata, Integer> iznos;
    public static volatile SingularAttribute<Uplata, String> svrha;
    public static volatile SingularAttribute<Uplata, Integer> brTransakcije;
    public static volatile SingularAttribute<Uplata, Racun> racun;
    public static volatile SingularAttribute<Uplata, String> nazivFilijale;
    public static volatile SingularAttribute<Uplata, Integer> idTra;

}