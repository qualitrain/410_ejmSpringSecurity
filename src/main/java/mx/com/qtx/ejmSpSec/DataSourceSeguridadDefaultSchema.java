package mx.com.qtx.ejmSpSec;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSeguridadDefaultSchema {
	private static final String NOMBRE_BD = "ejmBDSeguridad";
	private static final String CMD_INIC_BD = "createDatabaseIfNotExist=true";
	private static final String URL_BD = "jdbc:mysql://localhost:3306/" 
	                                     + NOMBRE_BD 
	                                     + "?serverTimezone=UTC&" 
	                                     + CMD_INIC_BD; 
	private static final String USER_BD = "root"; 
	private static final String PASSWD_BD = "root"; 
	
	private static Logger bitacora = LoggerFactory.getLogger(DataSourceSeguridadDefaultSchema.class);
	
	
	public DataSourceSeguridadDefaultSchema() {
	}
	
	@ConfigurationProperties(prefix = "qtx.datasource.sec")
	@Bean(name = "bdSeguridadDefault")
	public DataSource getDataSource() {
		DataSource dsSec =  DataSourceBuilder
				 	.create()
				 	.url(URL_BD)
		 			.username(USER_BD)
		 			.password(PASSWD_BD)
		 			.build();
		
		bitacora.info("Se ha instanciado data source mysql que apunta a BD:" + NOMBRE_BD);
		return dsSec;
	}

}
