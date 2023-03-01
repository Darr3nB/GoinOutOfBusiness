package com.ZSoos_Darren.GoingOutOfBusiness.helper;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class IncrementGenerator
        implements IdentifierGenerator {
//    private String tableName;
//    private String idColumn;
    private String queryString;
    public static final String TABLE_NAME = "tableName";
    public static final String ID_COLUMN = "id";

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        queryString = String.format("select MAX(%s) from %s",params.getProperty(ID_COLUMN,"id"),params.getProperty(TABLE_NAME));
        IdentifierGenerator.super.configure(type, params, serviceRegistry);
    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {

            final Long[] resultId = {null};

            sharedSessionContractImplementor.doWork(connection -> {
                try {
                    Statement statement = connection.createStatement();

                    ResultSet rs = statement.executeQuery(queryString);
                    if(rs.next()) {
                        long id = rs.getLong(1);
                        resultId[0] = id + 1;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            return resultId[0];
    }
}