import com.google.appengine.api.datastore.*

if (request.method == 'POST') {
    def twitterConfig = new Entity(KeyFactory.createKey('twitterConfig', 1))
    twitterConfig.username = params.tusername
    twitterConfig.password = params.tpassword
    twitterConfig.save()
    redirect 'index.groovy'
} else {
    html.html {
        body {
            center {
                div(style:'width:500px; text-align:left') {
                    fieldset {
                        legend('Twitter')
                        form(method:'post') {
                            input(type:'text', name:'tusername')
                            input(type:'password', name:'tpassword')
                            input(type:'submit', value:'save')
                        }
                    }
                    fieldset {
                        legend('Crons')
                        a(href:'collectTweets.groovy', target:'_blank', 'Tweets')
                        a(href:'collectFavorites.groovy', target:'_blank', 'Favorites')
                    }
                    a(href:userService.createLogoutURL('/'), 'logout')
                }
            }
        }
    }
}