/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseproject;

/**
 *
 * @author hadalkharouf
 */
public class ComboItem {
    private int id;
    private String name;

    public ComboItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // This is what will be shown in the JComboBox
    @Override
    public String toString() {
        return name;
    }
}
