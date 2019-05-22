package com.example.challengefast
import java.util.*

internal class Utility {
    companion object {
        val CountryDataSource: List<SpinnerItem> = object : ArrayList<SpinnerItem>() {
            init {
                add(SpinnerItem("", "Please select country", ""))
                add(SpinnerItem("1", "Afghanistan", "AF"))
                add(SpinnerItem("2", "Albania", "AL"))
                add(SpinnerItem("3", "Algeria", "AL"))
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
                add(SpinnerItem("23", "Bosnia and Herzegovina", "BA"))
                add(SpinnerItem("24", "Botswana", "BW"))
                add(SpinnerItem("19", "Brazil", "BR"))
                add(SpinnerItem("20", "Brunei", "BN"))
                add(SpinnerItem("21", "Bulgaria", "BG"))
                add(SpinnerItem("23", "Burkina Faso", "BF"))
                add(SpinnerItem("24", "Burundi", "BI"))


                add(SpinnerItem("91", "India", "IN"))
                add(SpinnerItem("58", "Venezuela", "VE"))
                add(SpinnerItem("84", "Vietnam", "VN"))
                add(SpinnerItem("1-284", "British Virgin Islands", "VG"))
                add(SpinnerItem("1-340", "US Virgin Islands", "VI"))
                add(SpinnerItem("681", "Wallis and Futuna", "WF"))
                add(SpinnerItem("212", "Western Sahara", "EH"))
                add(SpinnerItem("967", "Yemen", "YE"))
                add(SpinnerItem("260", "Zambia", "ZM"))
                add(SpinnerItem("263", "Zimbabwe", "ZW"))
            }
        }
    }
}