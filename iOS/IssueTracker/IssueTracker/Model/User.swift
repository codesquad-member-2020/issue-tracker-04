import Foundation

struct User: CustomStringConvertible, Model {
    let id: ID
    let name: String

    var description: String {
        name
    }
    
}

typealias UserCollection = [User]

