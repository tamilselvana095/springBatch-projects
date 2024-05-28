package com.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.batch.ProductProcessor;
import com.batch.entity.Product;
import com.batch.listener.MyListener;

@Configuration
@EnableBatchProcessing

public class BatchConfig {

	@Bean
	public FlatFileItemReader<Product>reader(){
		
		FlatFileItemReader<Product>reader=new FlatFileItemReader<Product>();
		reader.setResource(new PathResource("src/main/resources/products.csv"));
		reader.setLineMapper(new DefaultLineMapper<>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(DELIMITER_COMMA);
				setNames("prodId","prodCode","prodCost");
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
				setTargetType(Product.class);
			}});
		}});
		
		return reader;
	}
	
	
	@Bean
	public ItemProcessor<Product, Product>processor(){
		return new ProductProcessor();
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcBatchItemWriter<Product>writer(){
		
		JdbcBatchItemWriter<Product>writer=new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql("insert into products(pid,pcode,pcost,pdisc,pgst) values(:prodId,:prodCode,:prodCost,:prodDisc,:prodGst)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}
	
	@Bean
	public JobExecutionListener listener() {
		return new MyListener();
	}
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step step() {
		return stepBuilderFactory.get("step")
								 .<Product,Product>chunk(3)
								 .reader(reader())
								 .processor(processor())
								 .writer(writer())
								 .build();
	}
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
								.incrementer(new RunIdIncrementer())
								.listener(listener())
								.start(step())
								.build();
	}
	

	
}
