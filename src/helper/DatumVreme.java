package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatumVreme {
    public static String vratiDatumVreme () { return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()); }
}
