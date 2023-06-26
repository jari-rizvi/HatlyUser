package com.teamx.hatlyUser.ui.fragments.products.hatly

class ItemClass {
    // This variable ViewType specifies
    // which out of the two layouts
    // is expected in the given item
    var viewType: Int

    // getter and setter methods for the text variable
    // String variable to hold the TextView
    // of the first item.
    var text: String? = null

    // public constructor for the first layout
    constructor(viewType: Int, text: String?) {
        this.text = text
        this.viewType = viewType
    }

    // Variables for the item of second layout
    private var icon = 0
    var text_one: String? = null
    var text_two: String? = null

    // public constructor for the second layout
    constructor(
        viewType: Int, icon: Int, text_one: String?,
        text_two: String?
    ) {
        this.icon = icon
        this.text_one = text_one
        this.text_two = text_two
        this.viewType = viewType
    }

    // getter and setter methods for
    // the variables of the second layout
    fun geticon(): Int {
        return icon
    }

    fun seticon(icon: Int) {
        this.icon = icon
    }

    companion object {
        // Integers assigned to each layout
        // these are declared static so that they can
        // be accessed from the class name itself
        // And final so that they are not modified later
        const val LayoutOne = 0
        const val LayoutTwo = 1
    }
}