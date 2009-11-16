import com.google.appengine.api.datastore.*
import twitter4j.Twitter
import twitter4j.Query as Qry

/*
  - no param -   : Search with latest maxId.
  ?task          : Search all tweets using tasks.
  ?page=[pageNo] : Search tweets for requested page. And add task to search next page. 
 */

def searchKey = KeyFactory.createKey('search', 1)
def search

if (params.isEmpty()) {
    try {
        search = datastoreService.get(searchKey)
    } catch (EntityNotFoundException ignore) {}
}

def query = new Qry('明日やる OR あしたやる')
query.rpp = 100
if (params.page) {
    query.page = params.page.toInteger()
} else {
    query.sinceId = search ? search.sinceId : 0
}

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

search = search ?: new Entity(searchKey)
search.sinceId = result.maxId
search.save()

if (result.tweets && (params.task != null || params.page)) {
    def nextPage = (params.page?:'1').toInteger() + 1
    defaultQueue << [
        countdownMillis: 1000,
        url: '/admin/collectTweets.groovy',
        taskName: "collectTweets-page-${nextPage}-${new Date().format('yyyyMMddHHmmss')}",
        params:[page: nextPage]
    ]
}

println search.sinceId