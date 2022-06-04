package de.shakti.stromverbrauch.control;

import com.mongodb.WriteConcern;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import de.shakti.stromverbrauch.entity.Meter;
import de.shakti.stromverbrauch.entity.Position;
import de.shakti.stromverbrauch.entity.READINGTYPE;
import de.shakti.stromverbrauch.entity.Reading;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration= {EmbeddedMongoAutoConfiguration.class})
class MeterDAOImplIntegrationTest {
    @Autowired
    private MeterRepository mongoRepository;
    
    @Test
   public void save() {
        //arrange
        Meter meter = getMeter();
        //action
        Meter _actual=mongoRepository.save(meter);

        MeterDAOImpl meterDAO= new MeterDAOImpl(mongoRepository);
        meterDAO.save(meter);

        assertEquals(meter,_actual);


    }

    @Test
    void readMeter() {

        //arrange
        Meter meter = getMeter();
        //action
        mongoRepository.save(meter);
        Optional<Meter> _actual=mongoRepository.findById(meter.getId());

        MeterDAOImpl meterDAO= new MeterDAOImpl(mongoRepository);
        meterDAO.readMeter(meter.getId());

        assertEquals(meter.getId(),(_actual.get()).getId());
    }

    @Test
    void deleteMeter() {
    }

    private Meter getMeter() {
        Meter meter = Meter.builder()
                .id("200000")
                .position(Position.ELECTRICITY)
                .reading(Reading.builder().readingtype(READINGTYPE.HT).units(100)
                        .build()).build();
        return meter;
    }




        @Configuration
        static class MongoConfiguration implements InitializingBean, DisposableBean {

            MongodExecutable executable;

            @Override
            public void afterPropertiesSet() throws Exception {
                int port = 27019;

                MongodStarter starter = MongodStarter.getDefaultInstance();

                MongodConfig mongodConfig = MongodConfig.builder()
                        .version(Version.Main.V4_0)
                        .net(new Net(port, Network.localhostIsIPv6()))
                        .build();
                ;
                executable = starter.prepare(mongodConfig);
                executable.start();
            }


            @Bean
            public MongoDatabaseFactory factory() {
                return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27019/imager200_test");
            }


            @Bean
            public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
                MongoTemplate template = new MongoTemplate(mongoDbFactory);
                template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
                return template;
            }

            @Bean
            public MongoRepositoryFactoryBean mongoFactoryRepositoryBean(MongoTemplate template) {
                MongoRepositoryFactoryBean mongoDbFactoryBean = new MongoRepositoryFactoryBean(MeterRepository.class);
                mongoDbFactoryBean.setMongoOperations(template);

                return mongoDbFactoryBean;
            }

            @Override
            public void destroy() throws Exception {
                executable.stop();
            }
        }
    }
