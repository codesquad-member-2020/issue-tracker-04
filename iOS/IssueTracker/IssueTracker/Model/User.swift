import Foundation


struct User: CustomStringConvertible {
    let id: ID
    let name: String

    var description: String {
        name
    }
    
}
