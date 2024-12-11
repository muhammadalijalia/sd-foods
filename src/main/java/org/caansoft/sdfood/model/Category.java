package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "prestashop_category_id")
    private Integer prestashopCategoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrestashopCategoryId() {
        return prestashopCategoryId;
    }

    public void setPrestashopCategoryId(Integer prestashopCategoryId) {
        this.prestashopCategoryId = prestashopCategoryId;
    }
}
