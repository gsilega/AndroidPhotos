package model;

import java.io.Serializable;

public class Tag implements Serializable {

    // TODO: UPDATE SERIALVERSION UID BEFORE FINAL VERSION OF PROJECT
    private static final long serialVersionUID = 8402011406907161930L;
    /** tagType is a String indicating a class of tags*/
    private String tagType;
    /** tagValue is a String indicating an instance of a particular class of tags */
    private String tagValue;

    /**
     * Constructor
     * @param tagType
     * @param tagValue
     */
    public Tag(String tagType, String tagValue) {
        this.tagType = tagType;
        this.tagValue = tagValue;
    }
    /**
     * returns the type associated with this tag
     * @return tagType
     */
    public String getTagType() {
        return this.tagType;
    }
    /**
     * sets the type of this tag to a newTagType
     * @param newTagType
     */
    public void setTagType(String newTagType) {
        this.tagType = newTagType;
    }
    /**
     * sets the value of this tag to a new Tag Value
     * @param newTagValue
     */
    public void setTagValue(String newTagValue) {
        this.tagValue = newTagValue;
    }
    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Tag)) return false;
        else {
            if (this.getTagType().equalsIgnoreCase(((Tag)o).getTagType())&&
                    this.getTagValue().equalsIgnoreCase(((Tag)o).getTagValue()))
                return true;
            else return false;
        }
    }
    public String getTagValue() {
        return this.tagValue;

    }
    /**
     * returns the value of this tag as a String
     * @return tagValue
     */
    public String getString() {
        return this.tagValue;
    }

public String toString(){
        return getTagType() + " " + getTagValue();
}
}