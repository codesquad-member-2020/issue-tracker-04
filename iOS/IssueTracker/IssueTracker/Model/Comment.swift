import Foundation

struct Comment: Model {
    let id: ID
    let body: String
    var author: User?

    enum CodingKeys: String, CodingKey {
        case id
        case body = "content"
    }
}
