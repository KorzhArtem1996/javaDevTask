package ua.korzh.testtask.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.korzh.testtask.exception.NoSuchLocationException;
import ua.korzh.testtask.model.Address;
import ua.korzh.testtask.repository.LocationRepository;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Autowired
    private LocationRepository locationRepository;
    @Value("${cache.size}")
    private Integer cacheSize;
    @Value("${cache.expire.seconds}")
    private Integer cacheExpireSeconds;

    @Bean
    public CacheLoader<CoordinatePair, Address> cacheLoader() {

        return new CacheLoader<CoordinatePair, Address>() {
            @Override
            public Address load(CoordinatePair key) {
                var locationOptional = locationRepository.getLocationByLatAndLon(key.getLat(), key.getLon());
                if (locationOptional.isPresent()) {
                    return locationOptional.get().getAddress();
                }
                 throw new NoSuchLocationException();
            }
        };
    }

    @Bean
    public LoadingCache<CoordinatePair, Address> loadingCache() {

        LoadingCache<CoordinatePair, Address> cache = CacheBuilder.newBuilder()
                .maximumSize(cacheSize)
                .expireAfterAccess(cacheExpireSeconds, TimeUnit.SECONDS)
                .build(cacheLoader());
        return cache;
    }
}
