import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import static org.apache.commons.lang.time.DateUtils.*

// for Internet Explorer
if (headers['User-Agent'] =~ /MSIE/) {
    html.html {
        head { title '明日やるかも' }
        body (style:'text-align:center; margin-top:100px') {
            h1 'IEの対応は明日やる'
            a (href:'http://getfirefox.jp/') {
                img alt:'Firefoxをインストール', src:'http://getfirefox.jp/b/120x90_1_white', style:'border:0px'
            }
        }
    }
    return
}

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