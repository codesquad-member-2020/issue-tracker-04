import Foundation

struct IssueCollection {
    typealias IssueType = [Issue]

    private var elements: IssueType = .init()

    // MARK: - Mutating

    mutating func add(_ issue: IssueType.Element) {
        elements.append(issue)
    }

    mutating func insert(contentsOf issueList: Self, at row: Index) {
        elements.insert(contentsOf: issueList, at: row)
    }

    mutating func insert(newElement issue: Issue, at i: Index) {
        elements.insert(issue, at: i)
    }

    mutating func remove(at index: Index) {
        elements.remove(at: index)
    }

    // MARK: - Filter

    func filter(by status: Issue.Status) -> Self {
        return Self(elements: filter { $0.status == status })
    }

    func filter(by owner: User) -> Self {
        return Self(elements: filter { $0.owner == owner })
    }

    func filter(contains user: User) -> Self {
        return Self(elements: filter { $0.assignees.contains(user) })
    }

    func filter(_ isIncluded: (IssueType.Element) throws -> Bool) rethrows -> Self {
        return Self(elements: try elements.filter(isIncluded))
    }

    func filter(comment author: User) -> Self {
        return Self(elements: filter { $0.comments.contains(author: author) })
    }
}

extension IssueCollection: Collection {
    typealias Index = IssueType.Index
    typealias Element = IssueType.Element

    var startIndex: Index { elements.startIndex }
    var endIndex: Index { elements.endIndex }

    subscript(index: Index) -> Element {
        get { elements[index] }
        set { elements[index] =  newValue }
    }

    func index(after i: IssueType.Index) -> Index {
        elements.index(after: i)
    }
}

extension IssueCollection: ExpressibleByArrayLiteral {
    typealias ArrayLiteralElement = Element

    init(arrayLiteral elements: Element...) {
        self.elements = elements
    }
}
