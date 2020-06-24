import Foundation

struct Issue: Model {
    let id: ID
    var title: String
    var body: String?
    var comments: CommentCollection = .init()
    let owner: User
    var assignees: UserCollection = .init()
    var status: Status = .open

    mutating func addComment(_ comment: Comment) {
        comments.add(comment)
    }
}

extension Issue {
    enum Status: String, Codable {
        case open, closed
    }
}

struct BriefIssue: Model {
    let id: ID
    let title: String
    let body: String?
    var status: Issue.Status = .open

    enum CodingKeys: String, CodingKey {
        case id, title, status
        case body = "overview"
    }
}

struct PartialIssue: Codable {
    let title: String
    let body: String
}
