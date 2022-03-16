package com.nhxv.botbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "emoji", unique = true)
    private String emoji;

    @Column(name = "inner_sort")
    private int innerSort;

    @Column(name = "name")
    private String name;

    @Column(name = "rarity")
    private String rarity;

    @Column(name = "buy_price")
    private int buyPrice;

    @Column(name = "sell_price")
    private int sellPrice;

    @Column(name = "durability")
    private int durability;

    @Column(name = "description")
    private String description;
}
