package com.teamx.hatlyUser.utils


import com.google.gson.Gson

data class VariationOption(val title: String, val price: Int)
data class Variations(val color: String, val size: String)

fun main() {
    // Sample JSON data
    val variationsJson = """
        {
            "color": "Red",
            "size": "Medium"
        }
    """.trimIndent()

    val variationOptionsJson = """
        [
            {
                "title": "Red - Small",
                "price": 10
            },
            {
                "title": "Red - Medium",
                "price": 15
            },
            {
                "title": "Blue - Medium",
                "price": 12
            },
            {
                "title": "Green - Large",
                "price": 20
            }
        ]
    """.trimIndent()

    // Parsing JSON data using Gson
    val gson = Gson()
    val variations = gson.fromJson(variationsJson, Variations::class.java)
    val variationOptions = gson.fromJson(variationOptionsJson, Array<VariationOption>::class.java)

    // Getting color and size values from variations
    val color = variations.color
    val size = variations.size

    // Finding matching variation option based on color and size
    var selectedOption: VariationOption? = null
    for (option in variationOptions) {
        val title = option.title
        if (title.contains(color, true) && title.contains(size, true)) {
            selectedOption = option
            break
        }
    }

    // Getting price from selected variation option
    val price = selectedOption?.price

    // Printing the selected option's price
    if (price != null) {
        println("Selected option price: $$price")
    } else {
        println("No matching option found.")
    }
}
