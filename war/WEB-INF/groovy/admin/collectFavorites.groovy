import com.google.appengine.api.datastore.*
import twitter4j.Twitter

try {
    def twitterConfig = datastoreService.get(KeyFactory.createKey('twitterConfig', 1))
    def favorites = new Twitter(twitterConfig.username, twitterConfig.password).getFavorites()
    favorites.each {
        println "${it.text}<br/>"
    }
    memcacheService.put('favorites', favorites)
} catch (e) {
    println '<pre>'
    e.printStackTrace(out)
    println '</pre>'
}