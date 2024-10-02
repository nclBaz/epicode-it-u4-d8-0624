import com.github.javafaker.Faker;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) {

	/*	StringModifier dotsWrapper = str -> "..." + str + "...";
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

		// ------------------------------------------------------ SUPPLIER ---------------------------------------------------------------
		Supplier<Integer> randomIntSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 10000);
		};

		List<Integer> randomNumbers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			randomNumbers.add(randomIntSupplier.get());
		}

		System.out.println(randomNumbers);

		Supplier<User> usersSupplier = () -> {
			Faker faker = new Faker(Locale.ITALY);
			Random rndm = new Random();
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(1, 100));
		};

		List<User> randomUsers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			randomUsers.add(usersSupplier.get());
		}


		Supplier<List<User>> randomListSupplier = () -> {
			List<User> usersList = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				usersList.add(usersSupplier.get());
			}
			return usersList;
		};

		randomUsers.forEach(user -> System.out.println(user));*/
		// randomUsers.forEach(System.out::println); <-- Alternativa al codice di sopra più compatta


		// ------------------------------------------------------ STREAMS - FILTER ---------------------------------------------------------------

		Supplier<Integer> randomIntSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 10000);
		};

		List<Integer> randomNumbers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			randomNumbers.add(randomIntSupplier.get());
		}

		Supplier<User> usersSupplier = () -> {
			Faker faker = new Faker(Locale.ITALY);
			Random rndm = new Random();
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(1, 100));
		};

		Supplier<List<User>> randomListSupplier = () -> {
			List<User> usersList = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				usersList.add(usersSupplier.get());
			}
			return usersList;
		};

		List<User> randomUsers = randomListSupplier.get();
		// Gli Stream vanno sempre APERTI utilizzando .stream(), così facendo potrò sbloccare tutta una serie di operazioni INTERMEDIE
		// tipo .filter(), .map(), ecc. alla fine però per poter ottenere un qualche risultato utilizzabile dovrò anche CHIUDERE lo Stream
		// Chiudere lo Stream si può fare in più modi, ad esempio posso utilizzare un .forEach per magari stampare in console tutti gli elementi
		randomNumbers.forEach(number -> System.out.println(number));
		Stream<Integer> stream = randomNumbers.stream().filter(number -> number < 500 && number > 200);
		System.out.println("FILTER");
		stream.forEach(number -> System.out.println(number));

		// Nei filter posso o usare delle lambda scritte 'al volo' oppure anche magari utilizzare dei Predicate scritti in precedenza
		Predicate<Integer> isMoreThanZero = number -> number > 0;
		Predicate<Integer> isLessThanHundred = number -> number < 100;
		System.out.println("------------");
		randomNumbers.stream().filter(isMoreThanZero.and(isLessThanHundred)).forEach(num -> System.out.println(num));

		System.out.println("FILTER CON GLI USER");
		Predicate<User> isAgeLessThan18 = user -> user.getAge() < 18;
		randomUsers.stream().filter(isAgeLessThan18).forEach(user -> System.out.println(user));


		// ------------------------------------------------------ STREAMS - MAP ---------------------------------------------------------------
		System.out.println("------------------------------------------------------ STREAMS - MAP ---------------------------------------------------------------");
		randomUsers.stream().map(user -> user.getAge()).forEach(age -> System.out.println(age));
		// randomUsers.stream().map(User::getAge).forEach(System.out::println); // <-- Equivalente alla riga sopra
		randomUsers.stream().map(user -> user.getName()).forEach(name -> System.out.println(name));

		System.out.println("------------------------------------------------------ STREAMS - FILTER & MAP ---------------------------------------------------------------");
		randomUsers.stream().filter(user -> user.getAge() > 20).map(user -> user.getName()).forEach(name -> System.out.println(name));

		System.out.println("---------------------------------------------------- COME TERMINARE GLI STREAMS --------------------------------------------------------------------");

		System.out.println("------------------------------------------------------ STREAMS - ALLMATCH & ANYMATCH ---------------------------------------------------------------");
		// .some() e .every() di JS corrispondono a .anyMatch() e .allMatch() di Java
		// Controllano se ALMENO UN elemento (.some e .anyMatch) della lista passa una certa condizione
		// oppure se TUTTI gli elementi (.every e .allMatch) passano la condizione
		// Entrambi ritornano un BOOLEANO

		if (randomUsers.stream().allMatch(user -> user.getAge() > 17)) {
			System.out.println("Sono tutti maggiorenni");
		} else {
			System.out.println("C'è qualche minorenne");
		}

		if (randomUsers.stream().anyMatch(user -> user.getAge() > 17)) {
			System.out.println("C'è almeno un maggiorenne");
		} else {
			System.out.println("Sono tutti minorenni");
		}


		System.out.println("------------------------------------------------------ STREAMS - REDUCE ---------------------------------------------------------------");
		int totale = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.reduce(0, (accumulatore, elementoCorrente) -> accumulatore + elementoCorrente);
		System.out.println(totale);


		System.out.println("------------------------------------------------------ STREAMS - COME OTTENERE UNA LISTA DA UNO STREAM ---------------------------------------------------------------");
		List<Integer> usersAges = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.collect(Collectors.toList());

		List<User> minorenni = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.toList(); // .toList() è un'alternativa equivalente al .collect di sopra però ben più compatta

		System.out.println("------------------------------------------------------ DATE ---------------------------------------------------------------");
		LocalDate today = LocalDate.now();
		System.out.println(today);

		System.out.println("La data di domani è: " + today.plusDays(1));
		System.out.println("Il giorno di oggi lo scorso anno era: " + today.minusYears(1));

		LocalDate date = LocalDate.parse("2024-12-01");
		LocalDate date2 = LocalDate.of(2024, 12, 1);

		LocalDateTime orario = LocalDateTime.now();
		System.out.println(orario);


	}
}

/* Prima di Java 8 e dell'avvento delle LAMBDA FUNCTIONS avrei dovuto
crearmi classi che implementino l'interfaccia per poter avere quei metodi
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
