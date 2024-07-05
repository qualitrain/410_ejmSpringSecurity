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
	private static final String NOMBRE_BD_ESQ_DEFAULT = "ejmBDSeguridad";
	private static final String CMD_INIC_BD_ESQ_DEFAULT = "createDatabaseIfNotExist=true";
	private static final String URL_BD_ESQ_DEFAULT = "jdbc:mysql://localhost:3306/" 
	                                     + NOMBRE_BD_ESQ_DEFAULT 
	                                     + "?serverTimezone=UTC&" 
	                                     + CMD_INIC_BD_ESQ_DEFAULT; 
	private static final String USER_BD_ESQ_DEFAULT = "root"; 
	private static final String PASSWD_BD_ESQ_DEFAULT = "root"; 
	
	private static final String NOMBRE_BD_ESQ_PERSONALIZADO = "segbdperssv6";
	private static final String CMD_INIC_BD_ESQ_PERSONALIZADO = "createDatabaseIfNotExist=true";
	private static final String URL_BD_ESQ_PERSONALIZADO = "jdbc:mysql://localhost:3306/" 
	                                     + NOMBRE_BD_ESQ_PERSONALIZADO 
	                                     + "?serverTimezone=UTC&" 
	                                     + CMD_INIC_BD_ESQ_PERSONALIZADO; 
	private static final String USER_BD_ESQ_PERSONALIZADO = "root"; 
	private static final String PASSWD_BD_ESQ_PERSONALIZADO = "root"; 
	
	
	
	
	private static Logger bitacora = LoggerFactory.getLogger(DataSourceSeguridadDefaultSchema.class);
	
	
	public DataSourceSeguridadDefaultSchema() {
	}
	
//	@ConfigurationProperties(prefix = "qtx.datasource.sec")
//	@Bean(name = "bdSegdDefault")
	public DataSource getDataSourceEsquemaDefault() {
		DataSource dsSec =  DataSourceBuilder
				 	.create()
				 	.url(URL_BD_ESQ_DEFAULT)
		 			.username(USER_BD_ESQ_DEFAULT)
		 			.password(PASSWD_BD_ESQ_DEFAULT)
		 			.build();
		
		bitacora.info("Se ha instanciado data source mysql que apunta a BD:" + NOMBRE_BD_ESQ_DEFAULT);
		return dsSec;
	}
	
	@ConfigurationProperties(prefix = "qtx.datasource.sec")
	@Bean(name = "bdSegPersonalizada")
	public DataSource getDataSourceBdPersonalizadaSeguridad() {
		DataSource dsSec =  DataSourceBuilder
				 	.create()
				 	.url(URL_BD_ESQ_PERSONALIZADO)
		 			.username(USER_BD_ESQ_PERSONALIZADO)
		 			.password(PASSWD_BD_ESQ_PERSONALIZADO)
		 			.build();
		
		bitacora.info("Se ha instanciado data source mysql que apunta a BD:" + NOMBRE_BD_ESQ_PERSONALIZADO);
		return dsSec;
	}

}
