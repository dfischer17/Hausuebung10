/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Daniel Fischer
 */
public class Element {
    private String name;
    private String endTag;
    private boolean isTag;

    public Element(String name, boolean isTag) {
        this.name = name;
        this.isTag = isTag;
        this.endTag = "/" + this.name;
    }

    public String getName() {
        return name;
    }

    public boolean isTag() {
        return isTag;
    }

    public String getEndTag() {
        return endTag;
    }
        
    @Override
    public String toString() {
        return "Element{" + "name=" + name + ", isTag=" + isTag + '}';
    }   
}
