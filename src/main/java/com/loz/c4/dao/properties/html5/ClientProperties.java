package com.loz.c4.dao.properties.html5;

/**
 * Created by loz on 06/10/2017.
 */
public class ClientProperties {
    private Conviva conviva;
    private SumoLogic sumoLogic;
    private Integer autoplayLimit;
    private PromoAlert promoAlert;

    public Conviva getConviva() {
        return conviva;
    }

    public void setConviva(Conviva conviva) {
        this.conviva = conviva;
    }

    public SumoLogic getSumoLogic() {
        return sumoLogic;
    }

    public void setSumoLogic(SumoLogic sumoLogic) {
        this.sumoLogic = sumoLogic;
    }

    public Integer getAutoplayLimit() {
        return autoplayLimit;
    }

    public void setAutoplayLimit(Integer autoplayLimit) {
        this.autoplayLimit = autoplayLimit;
    }

    public PromoAlert getPromoAlert() {
        return promoAlert;
    }

    public void setPromoAlert(PromoAlert promoAlert) {
        this.promoAlert = promoAlert;
    }
}
