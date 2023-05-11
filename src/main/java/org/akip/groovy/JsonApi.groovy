package org.akip.groovy

import groovy.json.JsonOutput

class JsonApi {

    def toJson(def obj) {
        return JsonOutput.prettyPrint(JsonOutput.toJson(obj))
    }
}
