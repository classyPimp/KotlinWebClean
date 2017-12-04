package config.db.properties

class DbConnectionProperties(val map: MutableMap<String, Any?>) {
    var userName: String by map
    var password: String by map
    var url: String by map
    var driverClassName: String by map
}

