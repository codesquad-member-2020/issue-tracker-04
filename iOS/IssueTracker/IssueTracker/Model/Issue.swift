import Foundation

struct Issue {
    let id: ID
    let title: String
    let body: String?
    var comments: CommentCollection = .init()
    let owner: User
    var assignees: UserCollection = .init()
    var status: Status = .open

    func addComment(_ comment: Comment) {
    }
}

extension Issue {
    enum Status {
        case open, closed
    }
}


