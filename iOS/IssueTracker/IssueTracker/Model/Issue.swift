import Foundation

struct Issue {
    let id: ID
    let title: String
    let body: String?
    let comments: CommentCollection = .init()
    let owner: User
    let assignees: UserCollection = .init()
    var status: Status = .open

    func addComment(_ comment: Comment) {
    }
}

extension Issue {
    enum Status {
        case open, closed
        
    }

}

typealias IssueCollection = [Issue]
