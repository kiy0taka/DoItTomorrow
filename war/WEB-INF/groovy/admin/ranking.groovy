import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*

def query = new Query('tweet')
query.addSort("createdAt", Query.SortDirection.ASCENDING)
def tweets = datastoreService.prepare(query).asList(withOffset(0))

tweets.groupBy { it.fromUser }.sort { -(it.value.size()) }.entrySet().asType(List)[0..10].each {
    println "<div>${it.key}:${it.value.size()}</div>"
}

