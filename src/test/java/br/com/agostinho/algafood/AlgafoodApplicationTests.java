package br.com.agostinho.algafood;



import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

//@SpringBootTest
class AlgafoodApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testeString(){
		String multilineString = "Baeldung helps \n \n developers \n explore Java.";
		List<String> lines = multilineString.lines()
				.filter(line -> !line.isBlank())
				.map(String::strip)
				.collect(Collectors.toList());

//		Assert.assertThat(lines).containsExactly("Baeldung helps", "developers", "explore Java.");
		System.out.println(lines);
	}
}
