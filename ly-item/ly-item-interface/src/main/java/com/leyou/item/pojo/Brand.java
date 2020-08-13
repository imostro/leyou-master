package com.leyou.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Gray
 */

@Data
@ToString
@Table(name = "tb_brand")
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    private String image;

    private Character letter;

    public Brand(String name, String image, Character letter) {
        this.name = name;
        this.image = image;
        this.letter = letter;
    }
}
