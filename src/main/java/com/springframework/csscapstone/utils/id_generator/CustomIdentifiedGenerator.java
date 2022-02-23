package com.springframework.csscapstone.utils.id_generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomIdentifiedGenerator extends SequenceStyleGenerator {

    public static final String PREFIX_VALUE = "prefixValue";
    public static final String PREFIX_DEFAULT = "";
    public static final String NUMBER_FORMAT = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%d";
    private String prefixValue;
    private String numberFormat;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return prefixValue + String.format(numberFormat, super.generate(session, object));
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        prefixValue = ConfigurationHelper.getString(PREFIX_VALUE, params, PREFIX_DEFAULT);
        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT, params, NUMBER_FORMAT_DEFAULT);
    }
}
