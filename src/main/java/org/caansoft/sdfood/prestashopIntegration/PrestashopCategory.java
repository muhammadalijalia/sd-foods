package org.caansoft.sdfood.prestashopIntegration;

import org.json.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class PrestashopCategory {
    private Integer id;
    private List<PrestashopProductName> name;
    private PrestashopLinkRewrite linkRewrite;
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PrestashopProductName> getName() {
        return name;
    }

    public void setName(List<PrestashopProductName> name) {
        this.name = name;
    }
    @XmlElement(name = "link_rewrite")
    public PrestashopLinkRewrite getLinkRewrite() {
        return linkRewrite;
    }

    public void setLinkRewrite(PrestashopLinkRewrite linkRewrite) {
        this.linkRewrite = linkRewrite;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
