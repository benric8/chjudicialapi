package pe.gob.pj.hjudicial.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pe.gob.pj.pjseguridad.dao.config.ClientDataSourceRouter;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;
import pe.gob.pj.pjseguridad.dao.repository.PjSeguridadDao;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
	
	@Autowired
	private PjSeguridadDao pjSeguridadDao;
	
	/* Creación de conexión con base de datos seguridad */
	@Bean(name = "cxSeguridadDS")
	public DataSource jndiConexionSeguridad() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:jboss/datasources/historialJudicialSeguridadDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}	
		
	@Bean(name = "sessionSeguridad")
	public SessionFactory getSessionFactorySeguridad(@Qualifier("cxSeguridadDS") DataSource seguridadDS) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("pe.gob.pj.hjudicial.dao.entity.seguridad");
		sessionFactoryBean.setHibernateProperties(getHibernatePropertiesPostgresql());
		sessionFactoryBean.setDataSource(seguridadDS);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "txManagerSeguridad")
	public HibernateTransactionManager getTransactionManagerSeguridad(@Qualifier("sessionSeguridad") SessionFactory sessionSeguridad) throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionSeguridad);
		return transactionManager;
	} 

	/* Creación de conexión con base de datos HistorialJudicial */
	@Bean(name = "cxHJudicialDS")
	public DataSource jndiConexionHJudicial() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:jboss/datasources/historialJudicialDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}	
		
	@Bean(name = "sessionHJudicial")
	public SessionFactory getSessionFactoryHJudicial(@Qualifier("cxHJudicialDS") DataSource seguridadDS) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("pe.gob.pj.hjudicial.dao.entity.historialjudicial");
		sessionFactoryBean.setHibernateProperties(getHibernatePropertiesPostgresql());
		sessionFactoryBean.setDataSource(seguridadDS);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "txManagerHJudicial")
	public HibernateTransactionManager getTransactionManagerHJudicial(@Qualifier("sessionHJudicial") SessionFactory sessionSeguridad) throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionSeguridad);
		return transactionManager;
	} 
	
	private static Properties getHibernatePropertiesPostgresql() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		hibernateProperties.put("hibernate.show_sql", true);
		return hibernateProperties;
	}
	
	/* Creación de conexión con base de datos condenas sij_003(rnc) */
	@Bean(name = "cxCondenasDS")
	public DataSource consultaCotejoDs() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:jboss/datasources/historialJudicialCondenasDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}	
	
	@Bean(name = "txManagerCondenas")
	public DataSourceTransactionManager getTransactionManagerCotejo(@Qualifier("cxCondenasDS") DataSource hjudicialDs) throws IllegalArgumentException, NamingException, SQLException {
	      DataSourceTransactionManager tm = new DataSourceTransactionManager();
		  tm.setDataSource(hjudicialDs);
		  tm.setDefaultTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
		  tm.setRollbackOnCommitFailure(true);
		  tm.setGlobalRollbackOnParticipationFailure(true);
		  tm.afterPropertiesSet();
		  return tm;
	}
	
	@Bean(name = "jdbcTemplateCondenas")
	public JdbcTemplate cotejoJT(@Qualifier("cxCondenasDS") DataSource hjudicialDs) {
       return new JdbcTemplate(hjudicialDs);
	}
	
	/* Configuración de conexión a pjSeguridad */
	@Bean(name = "pjSeguridadDS")
	public DataSource jndiDataSourcePjSeguridad() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:/dsPjSeguridadCHJudicialDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
	
	@Bean(name = "pjSeguridadJdbcTemplate")
    @Autowired
    public JdbcTemplate pjSeguridadJdbcTemplate(@Qualifier("pjSeguridadDS") DataSource pjSeguridaDS) {
        return new JdbcTemplate(pjSeguridaDS);
    }
	
	
}