import Foundation

struct Issue {
    let id: ID
    var title: String
    var body: String?
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

extension Issue: Equatable {
    static func == (lhs: Issue, rhs: Issue) -> Bool {
        return lhs.id == rhs.id
    }
}

struct PartialIssue {
    let title: String
    let body: String
}
