import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * IBAN: provides an International Bank Account Number following the ISO 13616
 * IBAN Standard.
 * 
 * IBAN Structure (ISO 13616-1): - two-letter ISO 3166-1 country code - two
 * check digits - up to thirty alphanumeric characters for a BBAN (Basic Bank
 * Account Number)
 * 
 * 
 * 
 * @author Antoine De Gieter
 * 
 */
public class IBAN {
	private static final Map<Character, Integer> characterTranslationMap;
	private static final int divisor = 97;
	
	static {
		Map<Character, Integer> tmp = new HashMap<Character, Integer>();
		tmp.put('A', 10);
		tmp.put('B', 11);
		tmp.put('C', 12);
		tmp.put('D', 13);
		tmp.put('E', 14);
		tmp.put('F', 15);
		tmp.put('G', 16);
		tmp.put('H', 17);
		tmp.put('I', 18);
		tmp.put('J', 19);
		tmp.put('K', 20);
		tmp.put('L', 21);
		tmp.put('M', 22);
		tmp.put('N', 23);
		tmp.put('O', 24);
		tmp.put('P', 25);
		tmp.put('Q', 26);
		tmp.put('R', 27);
		tmp.put('S', 28);
		tmp.put('T', 29);
		tmp.put('U', 30);
		tmp.put('V', 31);
		tmp.put('W', 32);
		tmp.put('X', 33);
		tmp.put('Y', 34);
		tmp.put('Z', 35);
		characterTranslationMap = Collections.unmodifiableMap(tmp);
	}

	private String iban, bban;
	private String countryCode;
	private int countryDigits, checkDigits;
	private BigInteger number;
	private boolean valid;

	public IBAN(String iban) {
		this.iban = iban;
		this.countryCode = iban.substring(0, 2);
		this.checkDigits = Integer.parseInt(iban.substring(2, 4));
		this.bban = iban.substring(4);

		int countryCode_1, countryCode_2;
		countryCode_1 = characterTranslationMap.get(countryCode.charAt(0))
				.intValue();
		countryCode_2 = characterTranslationMap.get(countryCode.charAt(1))
				.intValue();
		this.countryDigits = Integer.parseInt(countryCode_1 + ""
				+ countryCode_2);

		StringBuilder numberStr = new StringBuilder();
		numberStr.append(this.bban);
		numberStr.append(this.countryDigits);
		numberStr.append(this.checkDigits);
		number = new BigInteger(numberStr.toString());

		int modulo = Integer.parseInt(number.divideAndRemainder(BigInteger.valueOf(divisor))[1].toString());
		
		this.valid = (1 == modulo);
	}

	public String getIban() {
		return iban;
	}

	public String getCountry() {
		return countryCode;
	}

	@Override
	public String toString() {
		return "IBAN [iban=" + iban + ", valid=" + valid + "]";
	}

	public static void main(String[] args) {
		System.out.println(new IBAN("GR1601101250000000012300695"));
	}
}
