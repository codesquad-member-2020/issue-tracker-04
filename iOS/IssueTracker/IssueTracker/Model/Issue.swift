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

struct IssueCollection {
    typealias IssueType = [Issue]

    var items: IssueType = .init()

    mutating func remove(at index: Index) {
        items.remove(at: index)
    }

    func filter(by status: Issue.Status) -> Self {
        return Self(items: filter { $0.status == status })
    }

    func filter(by owner: User) -> Self {
        return Self(items: filter { $0.owner == owner })
    }

    func filter(contains user: User) -> Self {
        return Self(items: filter { $0.assignees.contains(user) })
    }
}

extension IssueCollection: Collection {
    typealias Index = IssueType.Index
    typealias Element = IssueType.Element

    var startIndex: Index { items.startIndex }
    var endIndex: Index { items.endIndex }

    subscript(index: Index) -> Element { items[index] }

    func index(after i: IssueType.Index) -> Index {
        items.index(after: i)
    }
}

extension IssueCollection: ExpressibleByArrayLiteral {
    typealias ArrayLiteralElement = Element

    init(arrayLiteral elements: Element...) {
        self.items = elements
    }
}
