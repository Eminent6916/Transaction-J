package com.UserService.Seed;

import com.UserService.Model.Country;
import com.UserService.Repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CountryDataSeeder implements CommandLineRunner {

    private final CountryRepository countryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (countryRepository.count() == 0) {
            log.info("Seeding countries data...");

            List<Country> countries = Arrays.asList(
                    // Africa
                    new Country("Nigeria", "NG", "NGA", "+234", "NGN", "Naira", "₦", "Africa", "Western Africa", 9.0820, 8.6753, "🇳🇬", "U+1F1F3 U+1F1EC"),
                    new Country("Ghana", "GH", "GHA", "+233", "GHS", "Ghana Cedi", "₵", "Africa", "Western Africa", 7.9465, -1.0232, "🇬🇭", "U+1F1EC U+1F1ED"),
                    new Country("Kenya", "KE", "KEN", "+254", "KES", "Kenyan Shilling", "KSh", "Africa", "Eastern Africa", -0.0236, 37.9062, "🇰🇪", "U+1F1F0 U+1F1EA"),
                    new Country("South Africa", "ZA", "ZAF", "+27", "ZAR", "South African Rand", "R", "Africa", "Southern Africa", -30.5595, 22.9375, "🇿🇦", "U+1F1FF U+1F1E6"),
                    new Country("Egypt", "EG", "EGY", "+20", "EGP", "Egyptian Pound", "E£", "Africa", "Northern Africa", 26.8206, 30.8025, "🇪🇬", "U+1F1EA U+1F1EC"),

                    // North America
                    new Country("United States", "US", "USA", "+1", "USD", "US Dollar", "$", "North America", "Northern America", 37.0902, -95.7129, "🇺🇸", "U+1F1FA U+1F1F8"),
                    new Country("Canada", "CA", "CAN", "+1", "CAD", "Canadian Dollar", "C$", "North America", "Northern America", 56.1304, -106.3468, "🇨🇦", "U+1F1E8 U+1F1E6"),
                    new Country("Mexico", "MX", "MEX", "+52", "MXN", "Mexican Peso", "MX$", "North America", "Central America", 23.6345, -102.5528, "🇲🇽", "U+1F1F2 U+1F1FD"),

                    // Europe
                    new Country("United Kingdom", "GB", "GBR", "+44", "GBP", "British Pound", "£", "Europe", "Northern Europe", 55.3781, -3.4360, "🇬🇧", "U+1F1EC U+1F1E7"),
                    new Country("Germany", "DE", "DEU", "+49", "EUR", "Euro", "€", "Europe", "Western Europe", 51.1657, 10.4515, "🇩🇪", "U+1F1E9 U+1F1EA"),
                    new Country("France", "FR", "FRA", "+33", "EUR", "Euro", "€", "Europe", "Western Europe", 46.2276, 2.2137, "🇫🇷", "U+1F1EB U+1F1F7"),
                    new Country("Italy", "IT", "ITA", "+39", "EUR", "Euro", "€", "Europe", "Southern Europe", 41.8719, 12.5674, "🇮🇹", "U+1F1EE U+1F1F9"),

                    // Asia
                    new Country("China", "CN", "CHN", "+86", "CNY", "Chinese Yuan", "¥", "Asia", "Eastern Asia", 35.8617, 104.1954, "🇨🇳", "U+1F1E8 U+1F1F3"),
                    new Country("India", "IN", "IND", "+91", "INR", "Indian Rupee", "₹", "Asia", "Southern Asia", 20.5937, 78.9629, "🇮🇳", "U+1F1EE U+1F1F3"),
                    new Country("Japan", "JP", "JPN", "+81", "JPY", "Japanese Yen", "¥", "Asia", "Eastern Asia", 36.2048, 138.2529, "🇯🇵", "U+1F1EF U+1F1F5"),
                    new Country("South Korea", "KR", "KOR", "+82", "KRW", "South Korean Won", "₩", "Asia", "Eastern Asia", 35.9078, 127.7669, "🇰🇷", "U+1F1F0 U+1F1F7"),

                    // Middle East
                    new Country("United Arab Emirates", "AE", "ARE", "+971", "AED", "UAE Dirham", "د.إ", "Asia", "Western Asia", 23.4241, 53.8478, "🇦🇪", "U+1F1E6 U+1F1EA"),
                    new Country("Saudi Arabia", "SA", "SAU", "+966", "SAR", "Saudi Riyal", "ر.س", "Asia", "Western Asia", 23.8859, 45.0792, "🇸🇦", "U+1F1F8 U+1F1E6"),

                    // Oceania
                    new Country("Australia", "AU", "AUS", "+61", "AUD", "Australian Dollar", "A$", "Oceania", "Australia and New Zealand", -25.2744, 133.7751, "🇦🇺", "U+1F1E6 U+1F1FA"),
                    new Country("New Zealand", "NZ", "NZL", "+64", "NZD", "New Zealand Dollar", "NZ$", "Oceania", "Australia and New Zealand", -40.9006, 174.8860, "🇳🇿", "U+1F1F3 U+1F1FF"),

                    // South America
                    new Country("Brazil", "BR", "BRA", "+55", "BRL", "Brazilian Real", "R$", "South America", "South America", -14.2350, -51.9253, "🇧🇷", "U+1F1E7 U+1F1F7"),
                    new Country("Argentina", "AR", "ARG", "+54", "ARS", "Argentine Peso", "$", "South America", "South America", -38.4161, -63.6167, "🇦🇷", "U+1F1E6 U+1F1F7"),

                    // Additional African countries
                    new Country("Ethiopia", "ET", "ETH", "+251", "ETB", "Ethiopian Birr", "Br", "Africa", "Eastern Africa", 9.1450, 40.4897, "🇪🇹", "U+1F1EA U+1F1F9"),
                    new Country("Tanzania", "TZ", "TZA", "+255", "TZS", "Tanzanian Shilling", "TSh", "Africa", "Eastern Africa", -6.3690, 34.8888, "🇹🇿", "U+1F1F9 U+1F1FF"),
                    new Country("Uganda", "UG", "UGA", "+256", "UGX", "Ugandan Shilling", "USh", "Africa", "Eastern Africa", 1.3733, 32.2903, "🇺🇬", "U+1F1FA U+1F1EC"),
                    new Country("Rwanda", "RW", "RWA", "+250", "RWF", "Rwandan Franc", "FRw", "Africa", "Eastern Africa", -1.9403, 29.8739, "🇷🇼", "U+1F1F7 U+1F1FC"),
                    new Country("Cameroon", "CM", "CMR", "+237", "XAF", "Central African CFA Franc", "FCFA", "Africa", "Middle Africa", 7.3697, 12.3547, "🇨🇲", "U+1F1E8 U+1F1F2"),
                    new Country("Ivory Coast", "CI", "CIV", "+225", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 7.5400, -5.5471, "🇨🇮", "U+1F1E8 U+1F1EE"),
                    new Country("Senegal", "SN", "SEN", "+221", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 14.4974, -14.4524, "🇸🇳", "U+1F1F8 U+1F1F3"),
                    new Country("Morocco", "MA", "MAR", "+212", "MAD", "Moroccan Dirham", "د.م.", "Africa", "Northern Africa", 31.7917, -7.0926, "🇲🇦", "U+1F1F2 U+1F1E6"),
                    new Country("Algeria", "DZ", "DZA", "+213", "DZD", "Algerian Dinar", "د.ج", "Africa", "Northern Africa", 28.0339, 1.6596, "🇩🇿", "U+1F1E9 U+1F1FF"),
                    new Country("Tunisia", "TN", "TUN", "+216", "TND", "Tunisian Dinar", "د.ت", "Africa", "Northern Africa", 33.8869, 9.5375, "🇹🇳", "U+1F1F9 U+1F1F3"),
                    new Country("Zambia", "ZM", "ZMB", "+260", "ZMW", "Zambian Kwacha", "ZK", "Africa", "Eastern Africa", -13.1339, 27.8493, "🇿🇲", "U+1F1FF U+1F1F2"),
                    new Country("Zimbabwe", "ZW", "ZWE", "+263", "ZWL", "Zimbabwean Dollar", "Z$", "Africa", "Eastern Africa", -19.0154, 29.1549, "🇿🇼", "U+1F1FF U+1F1FC"),
                    new Country("Mozambique", "MZ", "MOZ", "+258", "MZN", "Mozambican Metical", "MT", "Africa", "Eastern Africa", -18.6657, 35.5296, "🇲🇿", "U+1F1F2 U+1F1FF"),
                    new Country("Angola", "AO", "AGO", "+244", "AOA", "Angolan Kwanza", "Kz", "Africa", "Middle Africa", -11.2027, 17.8739, "🇦🇴", "U+1F1E6 U+1F1F4"),
                    new Country("Botswana", "BW", "BWA", "+267", "BWP", "Botswana Pula", "P", "Africa", "Southern Africa", -22.3285, 24.6849, "🇧🇼", "U+1F1E7 U+1F1FC"),
                    new Country("Namibia", "NA", "NAM", "+264", "NAD", "Namibian Dollar", "N$", "Africa", "Southern Africa", -22.9576, 18.4904, "🇳🇦", "U+1F1F3 U+1F1E6"),
                    new Country("Mauritius", "MU", "MUS", "+230", "MUR", "Mauritian Rupee", "₨", "Africa", "Eastern Africa", -20.3484, 57.5522, "🇲🇺", "U+1F1F2 U+1F1FA"),
                    new Country("Seychelles", "SC", "SYC", "+248", "SCR", "Seychellois Rupee", "₨", "Africa", "Eastern Africa", -4.6796, 55.4920, "🇸🇨", "U+1F1F8 U+1F1E8"),
                    new Country("Liberia", "LR", "LBR", "+231", "LRD", "Liberian Dollar", "L$", "Africa", "Western Africa", 6.4281, -9.4295, "🇱🇷", "U+1F1F1 U+1F1F7"),
                    new Country("Sierra Leone", "SL", "SLE", "+232", "SLL", "Sierra Leonean Leone", "Le", "Africa", "Western Africa", 8.4606, -11.7799, "🇸🇱", "U+1F1F8 U+1F1F1"),
                    new Country("Gambia", "GM", "GMB", "+220", "GMD", "Gambian Dalasi", "D", "Africa", "Western Africa", 13.4432, -15.3101, "🇬🇲", "U+1F1EC U+1F1F2"),
                    new Country("Guinea", "GN", "GIN", "+224", "GNF", "Guinean Franc", "FG", "Africa", "Western Africa", 9.9456, -9.6966, "🇬🇳", "U+1F1EC U+1F1F3"),
                    new Country("Benin", "BJ", "BEN", "+229", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 9.3077, 2.3158, "🇧🇯", "U+1F1E7 U+1F1EF"),
                    new Country("Togo", "TG", "TGO", "+228", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 8.6195, 0.8248, "🇹🇬", "U+1F1F9 U+1F1EC"),
                    new Country("Burkina Faso", "BF", "BFA", "+226", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 12.2383, -1.5616, "🇧🇫", "U+1F1E7 U+1F1EB"),
                    new Country("Niger", "NE", "NER", "+227", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 17.6078, 8.0817, "🇳🇪", "U+1F1F3 U+1F1EA"),
                    new Country("Mali", "ML", "MLI", "+223", "XOF", "West African CFA Franc", "CFA", "Africa", "Western Africa", 17.5707, -3.9962, "🇲🇱", "U+1F1F2 U+1F1F1"),
                    new Country("Chad", "TD", "TCD", "+235", "XAF", "Central African CFA Franc", "FCFA", "Africa", "Middle Africa", 15.4542, 18.7322, "🇹🇩", "U+1F1F9 U+1F1E9"),
                    new Country("Sudan", "SD", "SDN", "+249", "SDG", "Sudanese Pound", "ج.س.", "Africa", "Northern Africa", 12.8628, 30.2176, "🇸🇩", "U+1F1F8 U+1F1E9"),
                    new Country("South Sudan", "SS", "SSD", "+211", "SSP", "South Sudanese Pound", "£", "Africa", "Eastern Africa", 6.8770, 31.3070, "🇸🇸", "U+1F1F8 U+1F1F8"),
                    new Country("Eritrea", "ER", "ERI", "+291", "ERN", "Eritrean Nakfa", "Nfk", "Africa", "Eastern Africa", 15.1794, 39.7823, "🇪🇷", "U+1F1EA U+1F1F7"),
                    new Country("Djibouti", "DJ", "DJI", "+253", "DJF", "Djiboutian Franc", "Fdj", "Africa", "Eastern Africa", 11.8251, 42.5903, "🇩🇯", "U+1F1E9 U+1F1EF"),
                    new Country("Somalia", "SO", "SOM", "+252", "SOS", "Somali Shilling", "Sh.so.", "Africa", "Eastern Africa", 5.1521, 46.1996, "🇸🇴", "U+1F1F8 U+1F1F4"),
                    new Country("Madagascar", "MG", "MDG", "+261", "MGA", "Malagasy Ariary", "Ar", "Africa", "Eastern Africa", -18.7669, 46.8691, "🇲🇬", "U+1F1F2 U+1F1EC"),
                    new Country("Malawi", "MW", "MWI", "+265", "MWK", "Malawian Kwacha", "MK", "Africa", "Eastern Africa", -13.2543, 34.3015, "🇲🇼", "U+1F1F2 U+1F1FC"),
                    new Country("Congo", "CG", "COG", "+242", "XAF", "Central African CFA Franc", "FCFA", "Africa", "Middle Africa", -0.2280, 15.8277, "🇨🇬", "U+1F1E8 U+1F1EC"),
                    new Country("DR Congo", "CD", "COD", "+243", "CDF", "Congolese Franc", "FC", "Africa", "Middle Africa", -4.0383, 21.7587, "🇨🇩", "U+1F1E8 U+1F1E9"),
                    new Country("Gabon", "GA", "GAB", "+241", "XAF", "Central African CFA Franc", "FCFA", "Africa", "Middle Africa", -0.8037, 11.6094, "🇬🇦", "U+1F1EC U+1F1E6"),
                    new Country("Equatorial Guinea", "GQ", "GNQ", "+240", "XAF", "Central African CFA Franc", "FCFA", "Africa", "Middle Africa", 1.6508, 10.2679, "🇬🇶", "U+1F1EC U+1F1F6"),
                    new Country("Central African Republic", "CF", "CAF", "+236", "XAF", "Central African CFA Franc", "FCFA", "Africa", "Middle Africa", 6.6111, 20.9394, "🇨🇫", "U+1F1E8 U+1F1EB"),
                    new Country("Burundi", "BI", "BDI", "+257", "BIF", "Burundian Franc", "FBu", "Africa", "Eastern Africa", -3.3731, 29.9189, "🇧🇮", "U+1F1E7 U+1F1EE"),
                    new Country("Comoros", "KM", "COM", "+269", "KMF", "Comorian Franc", "CF", "Africa", "Eastern Africa", -11.8750, 43.8722, "🇰🇲", "U+1F1F0 U+1F1F2"),
                    new Country("São Tomé and Príncipe", "ST", "STP", "+239", "STN", "São Tomé and Príncipe Dobra", "Db", "Africa", "Middle Africa", 0.1864, 6.6131, "🇸🇹", "U+1F1F8 U+1F1F9"),
                    new Country("Cape Verde", "CV", "CPV", "+238", "CVE", "Cape Verdean Escudo", "Esc", "Africa", "Western Africa", 16.5388, -23.0418, "🇨🇻", "U+1F1E8 U+1F1FB"),
                    new Country("Mauritania", "MR", "MRT", "+222", "MRU", "Mauritanian Ouguiya", "UM", "Africa", "Western Africa", 21.0079, -10.9408, "🇲🇷", "U+1F1F2 U+1F1F7"),
                    new Country("Libya", "LY", "LBY", "+218", "LYD", "Libyan Dinar", "ل.د", "Africa", "Northern Africa", 26.3351, 17.2283, "🇱🇾", "U+1F1F1 U+1F1FE"),
                    new Country("Lesotho", "LS", "LSO", "+266", "LSL", "Lesotho Loti", "L", "Africa", "Southern Africa", -29.6099, 28.2336, "🇱🇸", "U+1F1F1 U+1F1F8"),
                    new Country("Eswatini", "SZ", "SWZ", "+268", "SZL", "Swazi Lilangeni", "L", "Africa", "Southern Africa", -26.5225, 31.4659, "🇸🇿", "U+1F1F8 U+1F1FF")
            );

            try {
                countryRepository.saveAll(countries);
                log.info("Successfully seeded {} countries", countries.size());
            } catch (Exception e) {
                log.error("Error seeding countries data: {}", e.getMessage());
                throw e;
            }
        } else {
            log.info("Countries data already exists. Skipping seeding.");
        }
    }
}