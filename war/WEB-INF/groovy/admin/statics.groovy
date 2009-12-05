import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import static org.apache.commons.lang.time.DateUtils.*

def query = new Query('tweet')

def tweets = datastoreService.prepare(query).asList(withOffset(0))

def percent = {
    def max = it.max {it.value}.value
    it.collect { it.value = ((int)(it.value / max * 100)); it }
}

def h = percent(tweets.groupBy { addHours(it.createdAt, 9).hours }
    .sort { it.key }
    .collect { [title:it.key, value:it.value.size()] })

def fmt = new java.text.SimpleDateFormat('E', Locale.JAPANESE)
def d = percent(tweets.groupBy {fmt.format(addHours(it.createdAt, 9))}
    .sort { ['月', '火', '水', '木', '金', '土', '日'].indexOf it.key }
    .collect { [title:it.key, value:it.value.size()] })

html.html {
    table (border:1, cellspacing:0) {
        tr { h.each { th it.title } }
        tr { h.each { td align:'right', it.value } }
    }
    table (border:1, cellspacing:0) {
        tr { d.each { th it.title } }
        tr { d.each { td align:'right', it.value } }
    }
}

memcacheService.put('hourStatics', h)
memcacheService.put('dateStatics', d)
