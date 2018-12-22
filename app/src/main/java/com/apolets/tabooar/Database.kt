package com.apolets.tabooar

 val wordBank : ArrayList<Word> = arrayListOf(Word("Astronaut", arrayOf("Space","Nasa","Elon Musk")),
        Word("Bazooka", arrayOf("Army","Bomb","Terrorists")),
        Word("Book", arrayOf("School","Reading","Studying")),
        Word("Cactus", arrayOf("Desert","Water","Thorn")),
        Word("Cell Phone", arrayOf("Call","Talking","Communication")),
        Word("Cheeseburger", arrayOf("USA","Fast Food","Meat")),
        Word("Coffee", arrayOf("Energy","Morning","Black")),
        Word("Eagle", arrayOf("Bird","Bald","Predator")),
        Word("Earth", arrayOf("Planet","Sphere","Round")),
        Word("Elephant", arrayOf("Ivory","Nose","Big")),
        Word("Fire Truck", arrayOf("Water","Emergency","Drill")),
        Word("Fishing Boat", arrayOf("Ship","Rod","Sea")),
        Word("Fox", arrayOf("Canine","Tail","Animal")),
        Word("Gramophone", arrayOf("Old","Record","Music")),
        Word("Hot Dog", arrayOf("Mustard","Sausage","Fast Food")),
        Word("Harbor", arrayOf("Transport","Sea","Ship")),
        Word("Island", arrayOf("Survival","Alone","Ocean")),
        Word("Keyboard", arrayOf("Music","Piano","Computer")),
        Word("Knife", arrayOf("Sharp","Murder","Cutting")),
        Word("Microscope", arrayOf("Hospital","Bacteria","Virus")),
        Word("Muscle Car", arrayOf("Fast & Furious","American","Driving")),
        Word("Pagoda", arrayOf("Ancient","Building","China")),
        Word("Paper Plane", arrayOf("School","Flying","Toy")),
        Word("R2-D2", arrayOf("Beep-Boop","Robot","Star Wars")),
        Word("TV Remote", arrayOf("Control","Couch","IR")),
        Word("Space Shuttle", arrayOf("Space","Nasa","Elon Musk")),
        Word("Sword", arrayOf("Weapon","Medieval","Sharp")),
        Word("Telescope", arrayOf("Scope","Planets","Hubble")),
        Word("Treasure Chest", arrayOf("Pirate","Gold","Game")),
        Word("Train", arrayOf("Transportation","Commute","Rail")))



class Word(val word: String, val bannedWords: Array<String>)


var currentWordIndex = -1


fun nextWord() : Word{
    if ((currentWordIndex + 1) >= wordBank.size) {
        wordBank.shuffle()

        currentWordIndex = -1
    }

    currentWordIndex++

    return wordBank[currentWordIndex]

}