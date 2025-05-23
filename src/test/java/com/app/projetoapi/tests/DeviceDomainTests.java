package com.app.projetoapi.tests;


import com.app.projetoapi.MainTestClass;
import com.app.projetoapi.entity.DeviceDomain;
import com.app.projetoapi.entity.StateEnum;

public class DeviceDomainTests extends MainTestClass<DeviceDomain> {
    /**
     * Método que retorna o endpoint para a entidade
     * @return
     */
    public String getEndpoint() {
        return "/device-domain";
    }

    /**
     * Método que retorna um novo objeto da entidade
     * @return
     */
    @Override
    public DeviceDomain returnNewObject() {
        DeviceDomain deviceDomain = new DeviceDomain();
        deviceDomain.setState(StateEnum.AVAILABLE);
        return deviceDomain;
    }

    /**
     * Método que seta a propriedade do objeto
     * @param object
     * @param propertyValue
     */
    public void setProperty(DeviceDomain object, String propertyValue) {
        object.setName(propertyValue);
    }
}
