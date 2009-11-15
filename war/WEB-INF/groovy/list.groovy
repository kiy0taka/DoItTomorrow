import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import static org.apache.commons.lang.time.DateUtils.*

def query = new Query('tweet')
query.addSort("createdAt", Query.SortDirection.DESCENDING)
if (params.user) query.addFilter("fromUser", EQUAL, params.user)
if (params.status == 'today') {
    def from = addHours(truncate(addDays(new Date(), -1), Calendar.DATE), -9)
    def to = addDays(from, 1)
    query.addFilter("createdAt", GREATER_THAN_OR_EQUAL, from)
    query.addFilter("createdAt", Query.FilterOperator.LESS_THAN, to)
} else if (params.status == 'tomorrow') {
    def from = addHours(truncate(new Date(), Calendar.DATE), -9)
    def to = addDays(from, 1)
    query.addFilter("createdAt", GREATER_THAN_OR_EQUAL, from)
    query.addFilter("createdAt", Query.FilterOperator.LESS_THAN, to)
}
def pageNo = (params.page ?: '1').toInteger()
def tweets = datastoreService.prepare(query).asList(withOffset((pageNo - 1) * 20).limit(21))
request.hasNext = tweets.size() == 21
request.tweets = request.hasNext ? tweets[0..-2] : tweets

forward 'list.gtpl'