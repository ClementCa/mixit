package fr.mixit.model

class User(var id:Long, var name:String) {

    override fun toString(): String {
        return "fr.mixit.User(id='$id', name='$name')"
    }
}