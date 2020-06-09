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

typealias IssueCollection = [Issue]
