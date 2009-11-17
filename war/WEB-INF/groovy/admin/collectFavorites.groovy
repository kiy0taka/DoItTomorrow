import com.google.appengine.api.datastore.*
import twitter4j.Twitter

/*
  - no param -   : Get 20 most recent favorites.
  ?task          : Get all favorites using tasks.
  ?page=[pageNo] : Get requested page favorites. And add task to get next page. 
 */
try {
    def cfg = datastoreService.get(KeyFactory.createKey('twitterConfig', 1))
    def page = (params.page ?: '1').toInteger()
    def favorites = new Twitter(cfg.username, cfg.password).getFavorites(page)
    favorites.each { println "${it.text}<br/>" }

    def cache
    if (params.task != null) {
        memcacheService.delete('favorites')
        cache = [:]
    } else {
        cache = memcacheService.get('favorites') ?: [:]
    }
    favorites.each { cache[it.id] = it }
    memcacheService.put('favorites', cache)
    println cache.size()

    if (favorites && (params.task != null || params.page)) {
        defaultQueue << [
            countdownMillis: 1000,
            url: '/admin/collectFavorites.groovy',
            taskName: "collectFavorites-page-${++page}-${new Date().format('yyyyMMddHHmmss')}",
            params:[page: page]
        ]
    }
} catch (e) {
    out << '<pre>'
    e.printStackTrace(out)
    out << '</pre>'
}