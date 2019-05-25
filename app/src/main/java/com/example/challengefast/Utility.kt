package com.example.challengefast
import java.util.*
import kotlin.collections.ArrayList

internal class Utility {
    companion object {
        val CountryDataSource: List<SpinnerItem> = object : ArrayList<SpinnerItem>() {
            init {
                add(SpinnerItem("", "Please select country", ""))
                add(SpinnerItem("1", "Afghanistan", "AF"))
                add(SpinnerItem("2", "Albania", "AL"))
                add(SpinnerItem("3", "Algeria", "DZ"))
                add(SpinnerItem("4", "Andora", "AD"))
                add(SpinnerItem("5", "Angola", "AO"))
                add(SpinnerItem("6", "Antigua and Barbuda", "AG"))
                add(SpinnerItem("7", "Argentina", "AR"))
                add(SpinnerItem("8", "Armenia", "AM"))
                add(SpinnerItem("9", "Australia", "AU"))
                add(SpinnerItem("10", "Austria", "AT"))
                add(SpinnerItem("11", "Azerbaijan", "AZ"))

                add(SpinnerItem("12", "Bahamas", "BS"))
                add(SpinnerItem("13", "Bahrain", "BH"))
                add(SpinnerItem("14", "Bangladesh", "BD"))
                add(SpinnerItem("15", "Barbados", "BB"))
                add(SpinnerItem("16", "Belarus", "BY"))
                add(SpinnerItem("17", "Belgium", "BE"))
                add(SpinnerItem("18", "Beliz", "BZ"))
                add(SpinnerItem("19", "Benin", "BJ"))
                add(SpinnerItem("20", "Bhutan", "BT"))
                add(SpinnerItem("21", "Bolivia", "BO"))
                add(SpinnerItem("22", "Bosnia and Herzegovina", "BA"))
                add(SpinnerItem("23", "Botswana", "BW"))
                add(SpinnerItem("23", "Brazil", "BR"))
                add(SpinnerItem("24", "Brunei", "BN"))
                add(SpinnerItem("25", "Bulgaria", "BG"))
                add(SpinnerItem("26", "Burkina Faso", "BF"))
                add(SpinnerItem("27", "Burundi", "BI"))

                add(SpinnerItem("28", "Cambodia", "KH"))
                add(SpinnerItem("29", "Cameroon", "CM"))
                add(SpinnerItem("30", "Canada", "CA"))
                add(SpinnerItem("31", "Cape Verde", "CV"))
                add(SpinnerItem("32", "The Central African Republic", "CF"))
                add(SpinnerItem("33", "Chad", "TD"))
                add(SpinnerItem("34", "Chile", "CL"))
                add(SpinnerItem("45", "China", "CN"))
                add(SpinnerItem("35", "Colombia", "CO"))
                add(SpinnerItem("36", "The Comoros", "KM"))
                add(SpinnerItem("44", "Congo", "CG"))
                add(SpinnerItem("37", "Cook Islands", "CK"))
                add(SpinnerItem("38", "Costa Rica", "CR"))
                add(SpinnerItem("39", "Cote d'Ivoire", "CI"))
                add(SpinnerItem("40", "Croatia", "HR"))
                add(SpinnerItem("41", "Cuba", "CU"))
                add(SpinnerItem("42", "Cyprus", "CY"))
                add(SpinnerItem("43", "The Czech Republic", "CZ"))
                add(SpinnerItem("44", "The Democratic Republic of the Congo", "CD"))

                add(SpinnerItem("46", "Denmark", "DK"))
                add(SpinnerItem("47", "Djibouti", "DJ"))
                add(SpinnerItem("48", "Dominica", "DM"))
                add(SpinnerItem("49", "Dominican Republic", "DO"))

                add(SpinnerItem("50", "Ecuador", "EC"))
                add(SpinnerItem("51", "Egypt", "EG"))
                add(SpinnerItem("52", "Salvador", "SV"))
                add(SpinnerItem("53", "Equatorial Guinea", "GQ"))
                add(SpinnerItem("54", "Eritrea", "ER"))
                add(SpinnerItem("55", "Estonia", "EE"))
                add(SpinnerItem("56", "Ethiopia", "ET"))

                add(SpinnerItem("57", "Faroe Islands", "FO"))
                add(SpinnerItem("58", "Fiji", "FJ"))
                add(SpinnerItem("59", "Finland", "FI"))
                add(SpinnerItem("60", "France", "FR"))
                add(SpinnerItem("61", "French Polynesia", "PF"))

                add(SpinnerItem("62", "Gabon", "GA"))
                add(SpinnerItem("63", "Gambia", "GM"))
                add(SpinnerItem("64", "Georgia", "GE"))
                add(SpinnerItem("65", "Germany", "DE"))
                add(SpinnerItem("66", "Ghana", "GH"))
                add(SpinnerItem("67", "Greece", "GR"))
                add(SpinnerItem("68", "Greenland", "GL"))
                add(SpinnerItem("69", "Grenada", "GD"))
                add(SpinnerItem("70", "Guatemala", "GT"))
                add(SpinnerItem("71", "Guinia", "GN"))
                add(SpinnerItem("72", "Guinia Bissau", "GW"))

                add(SpinnerItem("73", "Haiti", "HT"))
                add(SpinnerItem("74", "Hendoras", "HN"))
                add(SpinnerItem("75", "Hungary", "HU"))

                add(SpinnerItem("76", "Iceland", "IS"))
                add(SpinnerItem("81", "India", "IN"))
                add(SpinnerItem("77", "Indonisia", "ID"))
                add(SpinnerItem("78", "Iran", "IR"))
                add(SpinnerItem("79", "Iraq", "IQ"))
                add(SpinnerItem("80", "Ireland", "IE"))
                add(SpinnerItem("78", "Italy", "IT"))

                add(SpinnerItem("79", "Jamaica", "JM"))
                add(SpinnerItem("80", "Japon", "JP"))
                add(SpinnerItem("82", "Jordan", "JO"))

                add(SpinnerItem("85", "Kazakhstan", "KZ"))
                add(SpinnerItem("83", "Kenia", "KE"))
                add(SpinnerItem("84", "Korea (north)", "KP"))
                add(SpinnerItem("86", "Korea (south)", "KR"))
                add(SpinnerItem("87", "Kuwait", "KW"))
                add(SpinnerItem("88", "Kyrgyzstan", "KG"))

                add(SpinnerItem("58", "Venezuela", "VE"))
                add(SpinnerItem("84", "Vietnam", "VN"))

                add(SpinnerItem("212", "Western Sahara", "EH"))

                add(SpinnerItem("967", "Yemen", "YE"))
                add(SpinnerItem("260", "Zambia", "ZM"))
                add(SpinnerItem("263", "Zimbabwe", "ZW"))
            }
        }
        val PostsDataSource: List<Post> = object :ArrayList<Post>(){
            init {
                add(Post("Collectif Iftar","#Iftar_Jama3i" , "Meet other Muslims. Get together and have a forum to discuss Islam in a \n" +
                        "non-judgmental environment. see more... ",R.drawable.iftar))
                add(Post("Ta7fid Qran","#60_7izb" , "Meet other Muslims. Get together and have a forum to discuss Islam in a \n" +
                        "non-judgmental environment. see more... ",R.drawable.ta7fid))
            }
        }
    }
}