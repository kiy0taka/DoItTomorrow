import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import static org.apache.commons.lang.time.DateUtils.*

def query = new Query('tweet')

// order by timestamp
query.addSort("createdAt", Query.SortDirection.DESCENDING)

// filter with user
if (params.user) query.addFilter("fromUser", EQUAL, params.user)

// filter with status (today|tomorrow)
if (params.status == 'today') {
    def to = addHours(truncate(addHours(new Date(), 9), Calendar.DATE), -9)
    def from = addDays(to, -1)
    query.addFilter("createdAt", GREATER_THAN_OR_EQUAL, from)
    query.addFilter("createdAt", Query.FilterOperator.LESS_THAN, to)
} else if (params.status == 'tomorrow') {
    def from = addHours(truncate(addHours(new Date(), 9), Calendar.DATE), -9)
    def to = addDays(from, 1)
    query.addFilter("createdAt", GREATER_THAN_OR_EQUAL, from)
    query.addFilter("createdAt", Query.FilterOperator.LESS_THAN, to)
}

def paging = withOffset(((params.page ?: '1').toInteger() - 1) * 20).limit(21)
def tweets = datastoreService.prepare(query).asList(paging)
request.hasNext = (tweets.size() == 21)
request.tweets = request.hasNext ? tweets[0..-2] : tweets

forward 'list.gtpl'