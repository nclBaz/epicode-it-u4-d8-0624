import entities.User;
import functional_interfaces.StringModifier;

import java.util.function.Predicate;

public class Main {
	public static void main(String[] args) {

		StringModifier dotsWrapper = str -> "..." + str + "...";
		StringModifier starsWrapper = str -> "***" + str + "***";
		// L'interfaccia di riferimento deve essere funzionale perché
		// così la lambda va ad implementare esattamente quell'unico metodo
		// e Java può controllare parametri e tipi di ritorno

		System.out.println(dotsWrapper.modify("CIAO"));
		System.out.println(starsWrapper.modify("CIAO"));

		StringModifier stringInverter = str -> { // Se ho bisogno di più righe di codice basta aprire le graffe
			String[] splitted = str.split("");
			String inverted = "";

			for (int i = splitted.length - 1; i >= 0; i--) {
				inverted += splitted[i];
			}
			return inverted;
		};

		System.out.println(stringInverter.modify("CIAO"));

		// ------------------------------------------------------ PREDICATES ---------------------------------------------------------------
		// Nelle parentesi angolari specifico il TIPO del parametro della lambda

		Predicate<Integer> isMoreThanZero = number -> number > 0;
		Predicate<Integer> isLessThanHundred = number -> number < 100;
		System.out.println(isMoreThanZero.test(-20));
		System.out.println(isMoreThanZero.test(20));
		System.out.println(isLessThanHundred.test(-20));
		System.out.println(isLessThanHundred.test(20));

		System.out.println(isMoreThanZero.and(isLessThanHundred).test(50));
		System.out.println(isMoreThanZero.negate().test(-20));

		User aldo = new User("Aldo", "Baglio", 20);
		Predicate<User> isAgeMoreThan17 = user -> user.getAge() > 17;

		System.out.println(isAgeMoreThan17.test(aldo));
	}
}

/* Prima di Java 8 e dell'avvento delle LAMBDA FUNCTIONS avrei dovuto
crearmi classi che implemementano l'interfaccia per poter avere quei metodi
public class DotsWrapper implements StringModifier{

	@Override
	public String modify(String str) {
		return "..." + str + "...";
	}
}

public class StarsWrapper implements StringModifier {
	@Override
	public String modify(String str) {
		return "***" + str + "***";
	}
}*/
