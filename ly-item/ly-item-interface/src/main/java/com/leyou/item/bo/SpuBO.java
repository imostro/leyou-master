package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Author: Gray
 */
@Data
@Table(name = "tb_spu")
public class SpuBO extends Spu {

    @Transient
    private String cname;

    @Transient
    private String bname;

    @Transient
    private SpuDetail spuDetail;

    @Transient
    List<Sku> skus;
}
