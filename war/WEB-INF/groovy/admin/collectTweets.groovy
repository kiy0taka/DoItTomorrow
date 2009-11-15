import com.google.appengine.api.datastore.*
import twitter4j.Twitter
import twitter4j.Query as Qr

def search
def searchKey = KeyFactory.createKey('search', 1)
try {
    search = datastoreService.get(searchKey)
} catch (EntityNotFoundException e) {
    search = new Entity(searchKey)
    search.sinceId = 0
}

def query = new Qr('明日やる')
query.rpp = 100
query.sinceId = search.sinceId
def result = new Twitter().search(query)
result.tweets.each {
    def tKey = KeyFactory.createKey('tweet', it.id)
    def tweet = new Entity(tKey)
    tweet.fromUser = it.fromUser
    tweet.fromUserId = it.fromUserId
    tweet.createdAt = it.createdAt
    tweet.text = it.text
    tweet.profileImageUrl = it.profileImageUrl
    tweet.save()
}

search.sinceId = result.maxId
search.save()

println search.sinceId