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

struct IssueCollection {
    typealias IssueType = [Issue]

    private var items: IssueType = .init()

    mutating func add(_ issue: IssueType.Element) {
        items.append(issue)
    }

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
        return filter { $0.assignees.contains(user) }
    }
    
    func filter(_ isIncluded: (IssueType.Element) throws -> Bool) rethrows -> Self {
        return Self(items: try items.filter(isIncluded))
    }
    
    func filter(comment author: User) -> Self {
        return Self(items: filter { $0.comments.contains(author: author) })
    }
    
    mutating func insert(contentsOf issueList: Self, at row: Int) {
        self.items.insert(contentsOf: issueList, at: row)
    }
}

extension IssueCollection: Collection {
    typealias Index = IssueType.Index
    typealias Element = IssueType.Element

    var startIndex: Index { items.startIndex }
    var endIndex: Index { items.endIndex }

    subscript(index: Index) -> Element {
        get { items[index] }
        set { items[index] =  newValue }
    }

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
