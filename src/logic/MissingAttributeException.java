package logic;

/**
 * Exception for Missing Attributes (which are previousy defined) in the deserialized JSON.
 * This Exception is thrown when a CSV is written and one item is at least one attribute is missing
 *
 * @author Patrik Simms
 */
class MissingAttributeException extends Exception {

    /**
     * constructor
     *
     * @param itemID            the id of the item which has at least one attribute missing
     * @param missingAttributes the name of the missing attribute
     */
    MissingAttributeException(String itemID, String missingAttributes) {
        super("At least 1 Missing Attribue. First one: [ " + missingAttributes + " ] in " +
                itemID + " .");
    }
}
