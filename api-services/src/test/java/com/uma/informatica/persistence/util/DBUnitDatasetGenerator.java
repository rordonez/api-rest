package com.uma.informatica.persistence.util;

import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import com.uma.informatica.persistence.configuration.ProductionDataSourceConfiguration;
import com.uma.informatica.persistence.configuration.ServiceContext;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by rafaordonez on 07/02/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceContext.class, ProductionDataSourceConfiguration.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@ActiveProfiles("production")
public class DBUnitDatasetGenerator {

    @Autowired
    DataSource dataSource;

    @Test
    public void generate() throws Exception{

        QueryDataSet queryDataSet = new QueryDataSet(new DatabaseDataSourceConnection(dataSource));

        queryDataSet.addTable("profesor");
        queryDataSet.addTable("pfc");
        queryDataSet.addTable("pfcs_profesores");
        queryDataSet.addTable("alumno");

        OutputStream outputStream = new FileOutputStream(new File("/restapi", "dataset.xml"));

        FlatXmlDataSet.write(queryDataSet, outputStream);

        outputStream.close();

    }
}
