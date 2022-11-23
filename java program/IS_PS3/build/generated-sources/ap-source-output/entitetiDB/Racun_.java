package entitetiDB;

import entitetiDB.Isplata;
import entitetiDB.Komitent;
import entitetiDB.Sana;
import entitetiDB.Uplata;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-07T18:35:16")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile ListAttribute<Racun, Sana> sanaList1;
    public static volatile ListAttribute<Racun, Isplata> isplataList;
    public static volatile ListAttribute<Racun, Uplata> uplataList;
    public static volatile SingularAttribute<Racun, Integer> dozvoljenMinus;
    public static volatile SingularAttribute<Racun, Integer> stanje;
    public static volatile SingularAttribute<Racun, Date> datumOtvaranja;
    public static volatile SingularAttribute<Racun, Integer> brTransakcija;
    public static volatile SingularAttribute<Racun, Komitent> komitent;
    public static volatile ListAttribute<Racun, Sana> sanaList;
    public static volatile SingularAttribute<Racun, Integer> idRac;
    public static volatile SingularAttribute<Racun, String> status;

}