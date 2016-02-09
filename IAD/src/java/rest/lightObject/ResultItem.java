/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.lightObject;

/**
 *
 * @author Omar
 */
public class ResultItem {

    private int id;
    private String word;
    private ResultItemType type;
    private String description;

    public ResultItem(int id, String word, ResultItemType type, String description) {
        this.id = id;
        this.word = word;
        this.type = type;
        this.description = description;
    }

    public ResultItem() {
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public ResultItemType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setType(ResultItemType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ResultItem{" + "id=" + id + ", word=" + word + ", type=" + type + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + (this.word != null ? this.word.hashCode() : 0);
        hash = 13 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 13 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultItem other = (ResultItem) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.word == null) ? (other.word != null) : !this.word.equals(other.word)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return !((this.description == null) ? (other.description != null) : !this.description.equals(other.description));
    }

}
