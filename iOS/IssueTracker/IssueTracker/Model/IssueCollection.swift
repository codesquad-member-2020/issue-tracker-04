import Foundation

struct IssueCollection {
    typealias IssueType = [Issue]

    private var items: IssueType = .init()

    // MARK: - Mutating

    mutating func add(_ issue: IssueType.Element) {
        items.append(issue)
    }

    mutating func insert(contentsOf issueList: Self, at row: Index) {
        items.insert(contentsOf: issueList, at: row)
    }

    mutating func insert(newElement issue: Issue, at i: Index) {
        items.insert(issue, at: i)
    }

    mutating func remove(at index: Index) {
        items.remove(at: index)
    }

    // MARK: - Filter

    func filter(by status: Issue.Status) -> Self {
        return Self(items: filter { $0.status == status })
    }

    func filter(by owner: User) -> Self {
        return Self(items: filter { $0.owner == owner })
    }

    func filter(contains user: User) -> Self {
        return Self(items: filter { $0.assignees.contains(user) })
    }

    func filter(_ isIncluded: (IssueType.Element) throws -> Bool) rethrows -> Self {
        return Self(items: try items.filter(isIncluded))
    }

    func filter(comment author: User) -> Self {
        return Self(items: filter { $0.comments.contains(author: author) })
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

extension IssueCollection {
    static func createFakeData() -> IssueCollection {
        let user = User(id: FakeID.userId, name: "모오오오스")

        return [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
    }
}
