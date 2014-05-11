package com.uma.informatica.persistence.util;

import com.uma.informatica.persistence.configuration.ProductionDataSourceConfiguration;
import com.uma.informatica.persistence.configuration.ServiceConfiguration;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by rafaordonez on 07/02/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, ProductionDataSourceConfiguration.class})
@ActiveProfiles("production")
public class DBUnitDatasetGenerator {

    @Inject
    DataSource dataSource;

    @Test
    public void generate() throws Exception{

        QueryDataSet queryDataSet = new QueryDataSet(new DatabaseDataSourceConnection(dataSource));

        queryDataSet.addTable("profesor");
        queryDataSet.addTable("pfc");
        queryDataSet.addTable("pfcs_profesores");
        queryDataSet.addTable("alumno");

        OutputStream outputStream = new FileOutputStream(new File(ServiceConfiguration.RESTAPI_STORAGE_DIRECTORY, "dataset.xml"));

        FlatXmlDataSet.write(queryDataSet, outputStream);

        outputStream.close();

    }
}
