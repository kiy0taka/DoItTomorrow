static toURL(href, params) {
    params.findAll { it.value }.collect {"${it.key}=${it.value}"}.join('&').with { it ? "$href?$it" : href}
}