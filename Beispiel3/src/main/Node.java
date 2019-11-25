/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Fischer
 */
public class Node {
    String tagName;
    private String content;
    private List<Element> elements;
    private List<Node> subNodeList = new ArrayList<>();

    public Node(String tagName, String content) {
        this.tagName = tagName;
        this.content = content;
        
        // Liste der Elemente; welche in content vorhanden sind erstellen
        elements = createElementsList();
        
        // Aus Elementen Nodes erzeugen und hinzufuegen
        subNodeList = createSubNodeList();
    }
    
    private List<Node> createSubNodeList() {
        List<Node> subNodeList = new ArrayList<>();
        
        // ueberpruefen ob content des Nodes nur noch content best (keine Tags)
        boolean isFinished = true;        
        for (Element element : elements) {
            if (element.isTag()) {
                isFinished = false;
            }
        }
        
        // wenn content nur content (keine Nodes) ausgeben
        if (isFinished) {
            System.out.println(content);
        }
        
        // wenn content noch Nodes enthaelt
        else {
            // neuen Node in subNodeListEinfuegen
            Element start = elements.get(0);
            String content = "";
            
            // solange das Ende des Nodes nicht
            int i = 1; 
            while (i + 1 < elements.size() && !elements.get(i).getName().equals(start.getEndTag())) {
                // Wenn Element Tag ist Klammer anhaengen
                if (elements.get(i).isTag()) {
                    content += "<" + elements.get(i).getName() + ">";
                }
                else {
                    content += elements.get(i).getName();
                }                
                i++;
            }
            
            
            subNodeList.add(new Node(start.getName(), content));
        }

        return subNodeList;        
    }
    
    private List<Element> createElementsList() {
        List<Element> elements = new ArrayList<>();
        
        // Zeile in einzelne Zeichen aufspalten
        char[] lineCharacters = content.toCharArray();
                
        // Zeichen der Zeile durchlaufen
        for (int i = 0; i < lineCharacters.length; i++) {
            
            // wenn tag 
            if (lineCharacters[i] == '<') {
                String tag = "";
                
                // tag zu Elements hinzufuegen
                while (lineCharacters[i + 1] != '>') {
                    tag += lineCharacters[i + 1];
                    i++;
                }
                
                // Tag hinzufugen
                elements.add(new Element(tag, true));
            }
            else {
                // wenn content
                String content = "";
                
                // content zu Elements hinzufuegen
                while (i + 1 < lineCharacters.length && lineCharacters[i + 1] != '<') {
                    content += lineCharacters[i + 1];
                    i++;
                }
                
                elements.add(new Element(content, false));
            }
        }
        
        // leere Stellen entfernen
        List<Element> toRemove = new ArrayList<>();
        
        // zu loeschende Elemente finden
        for (Element element : elements) {
            if (element.getName().equals("")) {
                toRemove.add(element);
            }
        }
        
        // leere Elemente loeschen
        elements.removeAll(toRemove);
        return elements;
    }

    @Override
    public String toString() {
        return "Node{" + "tag=" + tagName + ", content=" + content + '}';
    }        
}
