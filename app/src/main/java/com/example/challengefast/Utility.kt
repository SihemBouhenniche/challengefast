package com.example.challengefast
import com.example.challengefast.Models.Country
import com.example.challengefast.Models.Post


internal class Utility {
    companion object {
        val COUNTRY_DATA_SOURCE: List<Country> = object : ArrayList<Country>() {
            init {
                add(Country("", "Please select country", ""))
                add(Country("1", "Afghanistan", "AF"))
                add(Country("2", "Albania", "AL"))
                add(Country("3", "Algeria", "DZ"))
                add(Country("4", "Andora", "AD"))
                add(Country("5", "Angola", "AO"))
                add(Country("6", "Antigua and Barbuda", "AG"))
                add(Country("7", "Argentina", "AR"))
                add(Country("8", "Armenia", "AM"))
                add(Country("9", "Australia", "AU"))
                add(Country("10", "Austria", "AT"))
                add(Country("11", "Azerbaijan", "AZ"))

                add(Country("12", "Bahamas", "BS"))
                add(Country("13", "Bahrain", "BH"))
                add(Country("14", "Bangladesh", "BD"))
                add(Country("15", "Barbados", "BB"))
                add(Country("16", "Belarus", "BY"))
                add(Country("17", "Belgium", "BE"))
                add(Country("18", "Beliz", "BZ"))
                add(Country("19", "Benin", "BJ"))
                add(Country("20", "Bhutan", "BT"))
                add(Country("21", "Bolivia", "BO"))
                add(Country("22", "Bosnia and Herzegovina", "BA"))
                add(Country("23", "Botswana", "BW"))
                add(Country("23", "Brazil", "BR"))
                add(Country("24", "Brunei", "BN"))
                add(Country("25", "Bulgaria", "BG"))
                add(Country("26", "Burkina Faso", "BF"))
                add(Country("27", "Burundi", "BI"))

                add(Country("28", "Cambodia", "KH"))
                add(Country("29", "Cameroon", "CM"))
                add(Country("30", "Canada", "CA"))
                add(Country("31", "Cape Verde", "CV"))
                add(Country("32", "The Central African Republic", "CF"))
                add(Country("33", "Chad", "TD"))
                add(Country("34", "Chile", "CL"))
                add(Country("45", "China", "CN"))
                add(Country("35", "Colombia", "CO"))
                add(Country("36", "The Comoros", "KM"))
                add(Country("44", "Congo", "CG"))
                add(Country("37", "Cook Islands", "CK"))
                add(Country("38", "Costa Rica", "CR"))
                add(Country("39", "Cote d'Ivoire", "CI"))
                add(Country("40", "Croatia", "HR"))
                add(Country("41", "Cuba", "CU"))
                add(Country("42", "Cyprus", "CY"))
                add(Country("43", "The Czech Republic", "CZ"))
                add(Country("44", "The Democratic Republic of the Congo", "CD"))

                add(Country("46", "Denmark", "DK"))
                add(Country("47", "Djibouti", "DJ"))
                add(Country("48", "Dominica", "DM"))
                add(Country("49", "Dominican Republic", "DO"))

                add(Country("50", "Ecuador", "EC"))
                add(Country("51", "Egypt", "EG"))
                add(Country("52", "Salvador", "SV"))
                add(Country("53", "Equatorial Guinea", "GQ"))
                add(Country("54", "Eritrea", "ER"))
                add(Country("55", "Estonia", "EE"))
                add(Country("56", "Ethiopia", "ET"))

                add(Country("57", "Faroe Islands", "FO"))
                add(Country("58", "Fiji", "FJ"))
                add(Country("59", "Finland", "FI"))
                add(Country("60", "France", "FR"))
                add(Country("61", "French Polynesia", "PF"))

                add(Country("62", "Gabon", "GA"))
                add(Country("63", "Gambia", "GM"))
                add(Country("64", "Georgia", "GE"))
                add(Country("65", "Germany", "DE"))
                add(Country("66", "Ghana", "GH"))
                add(Country("67", "Greece", "GR"))
                add(Country("68", "Greenland", "GL"))
                add(Country("69", "Grenada", "GD"))
                add(Country("70", "Guatemala", "GT"))
                add(Country("71", "Guinia", "GN"))
                add(Country("72", "Guinia Bissau", "GW"))

                add(Country("73", "Haiti", "HT"))
                add(Country("74", "Hendoras", "HN"))
                add(Country("75", "Hungary", "HU"))

                add(Country("76", "Iceland", "IS"))
                add(Country("81", "India", "IN"))
                add(Country("77", "Indonisia", "ID"))
                add(Country("78", "Iran", "IR"))
                add(Country("79", "Iraq", "IQ"))
                add(Country("80", "Ireland", "IE"))
                add(Country("78", "Italy", "IT"))

                add(Country("79", "Jamaica", "JM"))
                add(Country("80", "Japon", "JP"))
                add(Country("82", "Jordan", "JO"))

                add(Country("85", "Kazakhstan", "KZ"))
                add(Country("83", "Kenia", "KE"))
                add(Country("84", "Korea (north)", "KP"))
                add(Country("86", "Korea (south)", "KR"))
                add(Country("87", "Kuwait", "KW"))
                add(Country("88", "Kyrgyzstan", "KG"))

                add(Country("58", "Venezuela", "VE"))
                add(Country("84", "Vietnam", "VN"))

                add(Country("212", "Western Sahara", "EH"))

                add(Country("967", "Yemen", "YE"))
                add(Country("260", "Zambia", "ZM"))
                add(Country("263", "Zimbabwe", "ZW"))
            }
        }

        var PostsDataSource = arrayListOf<Post>(Post("Collectif Iftar", "#Iftar_Jama3i", "Meet other Muslims. Get together and have a forum to discuss Islam in a \n" +
                "non-judgmental environment. see more... ", ""),Post("Ta7fid Qran", "#60_7izb", "Meet other Muslims. Get together and have a forum to discuss Islam in a \n" +
                "non-judgmental environment. see more... ", ""))

        /*var PostsDataSource = arrayListOf<Post>(Post("Collectif Iftar", "#Iftar_Jama3i", "Meet other Muslims. Get together and have a forum to discuss Islam in a \n" +
                "non-judgmental environment. see more... ", ""),Post("Ta7fid Qran", "#60_7izb", "Meet other Muslims. Get together and have a forum to discuss Islam in a \n" +
                "non-judgmental environment. see more... ", ""))*/


    }

}