package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Gray
 */

@Data
@Table(name = "tb_sku")
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spu_id")
    private Long spuId;

    private String title;

    private String images;

    private Long price;

    private String indexes;

    @Column(name = "own_spec")
    private String ownSpec;

    private Boolean enable;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;


    /**
     * 库存
     */
    @Transient
    private Integer stock;
}
