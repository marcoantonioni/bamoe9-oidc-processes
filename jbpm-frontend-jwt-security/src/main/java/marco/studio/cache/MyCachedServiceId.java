package marco.studio.cache;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import io.quarkus.arc.Unremovable;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CaffeineCache;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Unremovable
public class MyCachedServiceId {

  // https://quarkus.io/guides/cache

  @CacheName("myServiceId")
  Cache cache;

  public Uni<String> newServiceId(String key) {
    String id = new String(Base64.getEncoder().encode(new StringBuffer("SID-").append(key).append("-").append(UUID.randomUUID()).toString().getBytes()));
    cache.as(CaffeineCache.class).put(id, CompletableFuture.completedFuture(id));
    Log.trace("===>>> MyCachedServiceId newServiceId: " + id);
    return Uni.createFrom().item(id);
  }

  public void invalidate(String id) {
    Log.trace("===>>> MyCachedServiceId invalidate: " + id);
    cache.as(CaffeineCache.class).invalidate(id);
  }

  public CompletableFuture<String> getIfPresent(String key) {
    if (cache.as(CaffeineCache.class).keySet().contains(key)) {      
      Log.trace("===>>> MyCachedServiceId getIfPresent found key: " + key);
      return cache.as(CaffeineCache.class).getIfPresent(key);
    }
    Log.trace("===>>> MyCachedServiceId getIfPresent not found key: " + key);
    return CompletableFuture.completedFuture("");
  }

  public String generateServiceId(String keyId) {
    String _key = "k/n/a";
    try {
      _key = newServiceId(keyId).subscribe().asCompletionStage().get();
    } catch (Throwable t) {
      t.printStackTrace();
      Log.trace("generateServiceId", t);
    }
    return _key;
  }

}
