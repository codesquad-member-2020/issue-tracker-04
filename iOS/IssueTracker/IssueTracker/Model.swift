import Foundation

struct Issue {
    let id: ID
    let title: String
    let body: String?
    let comments: CommentCollection = .init()
    let owner: User

    func addComment(_ comment: Comment) {

    }
}

struct User: CustomStringConvertible {
    let id: ID
    let name: String

    var description: String {
        name
    }
}

typealias CommentCollection = [Comment]

struct Comment {
    let id: ID
    let body: String
    let author: User
}

typealias ID = Int
