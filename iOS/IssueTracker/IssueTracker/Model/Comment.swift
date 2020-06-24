import Foundation

struct Comment: Model {
    let id: ID
    let body: String
    let author: User
}
