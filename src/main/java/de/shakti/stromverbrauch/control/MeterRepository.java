package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.Meter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public interface MeterRepository extends MongoRepository<Meter, String> {
    @Query("{position:'?0'}")
    Meter findMeterByPosition(String position);
}
