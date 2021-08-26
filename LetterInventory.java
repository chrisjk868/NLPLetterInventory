
// Christopher Ku
// Section: AG with Jiamae Wang
// Assessment 2: LetterInventory
//
// This LetterInventory class represents an inventory of 26 letters
// within the English alphabet. The LetterInventory class has its own
// size representing the total number of characters stored within the
// inventory. In doing so the LetterInventory data type could store
// a character by character count vectorization for letters
// in the english alphabet.

public class LetterInventory {
    private int[] elementData; // Stores individual character counts
    private int size; // Total character count of each letter combined
    public static final int ALPHABET_SIZE = 26;

    /**
     * Constructs a LetterInvetory object by taking in a String
     * to initalize its size and character counts for each
     * alphabet within the passed in String, and also converts
     * the String letters to lower cases.
     *
     * @param data   a non-null String
     */
    public LetterInventory(String data) {
        this.elementData = new int[ALPHABET_SIZE];
        this.size = 0;
        String dataLowerCase = data.toLowerCase();
        for (int i = 0; i < dataLowerCase.length(); i++) {
            if (Character.isLetter(dataLowerCase.charAt(i))) {
                this.elementData[dataLowerCase.charAt(i) - 'a']++;
                this.size++;
            }
        }
    }

    /**
     * pre: The passed in letter parameter must not be an non alphabetic
     *      character or else it will throw an IllegalArguementException
     *      if this precondition isn't met.
     *
     * post: Takes a primitive type character named letter and returns an integer
     *       that represents the total count of how many times this letter
     *       is in the inventory.
     *
     * @param letter   a character primitive type, which is case insensitive
     *
     * @return count   the integer representing the total count of how many times
     *                 the given letter appears in the inventory.
     */
    public int get(char letter) {
        if (!Character.isLetter(Character.valueOf(letter))) {
            throw new IllegalArgumentException();
        }
        int count = this.elementData[Character.toLowerCase(letter) - 'a'];
        return count;
    }

    /**
     * pre: The passed in letter parameter must not be an non alphabetic
     *      character and the passed in value >= 0. Or else if these preconditions
     *      aren't met the method throws an IllegalArguementException.
     *
     * post: Takes in 2 parameters one being a letter of primitive type character
     *       and one being a value of type integer. Then sets the total count of
     *       the given letter within the inventory to the given value.
     *
     * @param letter   a primitive type character for specifying which letter's
     *                 total count would be changed in the inventory.
     *
     * @param value   an integer specifying the new value that the letter count will
     *                be set to
     */
    public void set(char letter, int value) {
        if (!Character.isLetter(Character.valueOf(letter)) || value < 0) {
            throw new IllegalArgumentException();
        }
        this.size -= this.elementData[Character.toLowerCase(letter) - 'a'];
        this.elementData[Character.toLowerCase(letter) - 'a'] = value;
        this.size += value;
    }

    /**
     * @return this.size   integer value of the size of letter inventory
     */
    public int size() {
        return this.size;
    }

    /**
     * returns true if the size of the letter inventory is zero
     * returns false if the size of the letter inventory isn't
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns a String representation of every letter in lower case
     * in alphabetical order that could be repeated by their individual 
     * counts and enclosed by square brackets.
     *
     * @return concatString   a non null String
     */
    public String toString() {
        String concatString = "[";
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char targetTerm = (char)('a' + i);
            String termToString = Character.toString(targetTerm);
            for (int j = 0; j < this.get((char)('a' + i)); j++) {
                concatString += termToString;
            }
        }
        return concatString + "]";
    }

    /**
     * Takes in a letter inventory and adds the character by character
     * count of each letter with the current letter inventory then stores
     * those counts in a new letter inventory, which is returned by the method
     *
     * @param other   a LetterInventory data type with letters and character counts
     *
     * @return sumInventory   a new LetterInventory that consists of letters and character
     *                        counts by summing both the current letter inventory and
     *                        the one that is taken into the method.
     */
    public LetterInventory add(LetterInventory other) {
        LetterInventory sumInventory = new LetterInventory(" ");
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char currentTerm = (char)('a' + i);
            int newRepeats = this.get(currentTerm) + other.get(currentTerm);
            sumInventory.set(currentTerm, newRepeats);
        }
        return sumInventory;
    }

    /**
     * Takes in a letter inventory and subtracts the character by charcter
     * count of each letter with the current letter inventory then stores
     * those counts in a new letter inventory, which is returned by the method.
     *
     * @param other a LetterInvetory data type with letters and character count
     *
     * @return subtractInventory   a new LetterInventory that consists of letters and
     *                             characters counts by subtracting both the current
     *                             letter inventory and the one the is taken into the
     *                             method.
     *
     * @return null   returns null if at any point the current letter inventory's
     *                character count for a specific letter is less than the character
     *                count of that letter in the letter inventory is taken in as a
     *                parameter for this method.
     */
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory subtractInventory= new LetterInventory(" ");
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char setChar = (char)('a'+ i);
            int difference = this.get(setChar) - other.get(setChar);
            if (difference < 0) {
                return null;
            }
            subtractInventory.set(setChar, difference);
        }
        return subtractInventory;
    }

    // Returns true if the other object stores the same character counts as this inventory.
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LetterInventory)) {
            return false;
        }
        LetterInventory other = (LetterInventory) o;
        LetterInventory diff = this.subtract(other);
        return diff != null && diff.isEmpty();
    }

    // Returns a hash code value for this letter inventory.
    public int hashCode() {
        int result = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            result += c * get(c);
        }
        return result;
    }

    // Returns the cosine similarity between this inventory and the other inventory.
    public double similarity(LetterInventory other) {
        long product = 0;
        double thisNorm = 0;
        double otherNorm = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            int a = this.get(c);
            int b = other.get(c);
            product += a * (long) b;
            thisNorm += a * a;
            otherNorm += b * b;
        }
        if (thisNorm <= 0 || otherNorm <= 0) {
            return 0;
        }
        return product / (Math.sqrt(thisNorm) * Math.sqrt(otherNorm));
    }

    public static void main(String[] args) {
        LetterInventory inventory = new LetterInventory("washington state");
        System.out.println(inventory.toString());
    }
}
