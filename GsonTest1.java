package gsontest;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
/**
 * Kurzbeweis der Funktionalität der JSON-Bibliothek Gson (Hersteller: Google)
 * Download unter: http://search.maven.org/#artifactdetails|com.google.code.gson|gson|2.3.1|jar
 * Lizenz der Bibliothek: Open Source (Apache License 2.0)
 * Objekt wird zu JSON serialisiert, ausgegeben, deserialiert und erneut ausgegeben
 * @author Sebastian Kopp
 *
 */
 
 // Achja: Kompilieren dürfts ihr selber ;)
public class GsonTest1 {
	public static void main(String[] args) {
		Gson gs = new Gson();
		String json;
		System.out.println(json = gs.toJson(new FooObj()));
		
		//System.out.println(gs.toString());
		FooObj fo = gs.fromJson(json, FooObj.class);
		System.out.println("Abschluss::::");
		System.out.println(fo.toString());
	}
}
/**
 * De Implementierung des Interface Serializable scheint hier gar nicht nötig zu sein...
 * @author Sebastian
 */
class FooObj{
	private int a;
	private String b;
	private String[] c;
	private BarObj bo;
	private BarObj bo2;
	public FooObj() {
		a = 42;
		b = "lksdjflk";
		c = new String[]{"lkjsdfkjnvs ", "slkdjf jl", "ks djgksdj sdjsd"};
		bo = new BarObj();
		bo2 = null;
	}
	private String throwOutArray(Object[]arr){
		final StringBuilder rc = new StringBuilder("<");
		Arrays.stream(arr).forEach(e -> rc.append(e.toString()+", "));
		return rc.toString() + ">";
	}
	@Override
	public String toString(){
		return String.format("Ein FooObj: a=%d, b=%s, c=%s, {bo=%s}, {bo2=%s}", a, b, throwOutArray(c),
				(bo != null)?bo.toString():"xx", (bo2 != null)?bo2.toString():"xx");
	}
}
/**
 * Um zu beweisen, dass Class Within a Class funktioniert...
 * @author Sebastian Kopp
 */
class BarObj{
	private int f;
	private String h;
	private List<String> l;
	public BarObj(){
		f = 420815;
		h = "Irgendwo im nirchendwo";
		l = Arrays.asList("Dies", "ist", "ein", "Beispiel", "für", "eine", "Liste");
	}
	private String listToString(List<String> lx){
		StringBuilder sb = new StringBuilder("L:<<");
		lx.forEach(sb::append);	// Bsp. f. Java-8-Methodenreferenzen, dafür wird der String ohne Trennzeichen erstellt
		return sb.toString() + ">>";
	}
	@Override
	public String toString(){
		return "f: " + f + ", h: " + h + "l: " + listToString(l);
	}
}
