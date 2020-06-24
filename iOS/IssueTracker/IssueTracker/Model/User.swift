import Foundation

struct User: CustomStringConvertible {
    let id: ID
    let name: String

    var description: String {
        name
    }
    
}

extension User: Equatable { }

typealias UserCollection = [User]

extension User: Hashable { }
extension User: Codable { }
